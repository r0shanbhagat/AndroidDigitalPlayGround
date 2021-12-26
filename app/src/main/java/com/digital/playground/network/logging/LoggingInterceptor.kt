package com.digital.playground.network.logging

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class LoggingInterceptor(private val mBuilder: Builder) : Interceptor {
    private val isDebug: Boolean
    private val logger: Logger?

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        /*
         *Log Disabled in the case of Level.NONE || Release build
         */if (!isDebug || mBuilder.level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = mBuilder.level == Level.BODY
        val logHeaders = logBody || mBuilder.level == Level.HEADERS
        val requestBody = request.body()
        val hasRequestBody = requestBody != null
        val startNsRequest = System.nanoTime()
        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        var requestStartMessage =
            "--> REQUEST START " + request.url() + ' ' + request.method() + ' ' /*+ requestPath(request.httpUrl()) + ' '*/ + protocol(
                protocol
            )
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody!!.contentLength() + "-byte body)"
        }
        logger!!.log(requestStartMessage)
        if (logHeaders) {
            val headers = request.headers()
            if (null != headers && headers.size() > 0) {
                logger.log("Headers START -->")
                var i = 0
                val count = headers.size()
                while (i < count) {
                    logger.log(headers.name(i) + ": " + headers.value(i))
                    i++
                }
                logger.log("<-- Headers END")
            }
            var endMessage = "--> REQUEST END " //+ request.method();
            if (logBody && hasRequestBody) {
                val buffer = Buffer()
                requestBody!!.writeTo(buffer)
                val contentType = requestBody.contentType()
                contentType?.charset(UTF8)
                logger.log("")
                val tookMsRequest =
                    TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNsRequest)
                endMessage += " (" + requestBody.contentLength() + "-byte body) (" + tookMsRequest + "ms)"
            }
            logger.log(endMessage)
        }
        val startNs = System.nanoTime()
        val response = chain.proceed(request)
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val responseBody = response.body()
        logger.log(
            "<-- RESPONSE START " + protocol(response.protocol()) + ' ' + response.code() + ' '
                    + response.message() + " (" + tookMs + "ms"
                    + (if (!logHeaders) ", " + responseBody!!.contentLength() + "-byte body" else "") + ')'
        )
        if (logHeaders) {
            val headers = response.headers()
            if (null != headers) {
                logger.log("Headers START -->")
                var i = 0
                val count = headers.size()
                while (i < count) {
                    logger.log(headers.name(i) + ": " + headers.value(i))
                    i++
                }
                logger.log("<-- Headers END")
            }
            var endMessage = "<-- RESPONSE END "
            if (logBody) {
                val source = responseBody!!.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()
                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)
                }
                if (responseBody.contentLength() != 0L) {
                    logger.log("\n")
                    logger.log(buffer.clone().readString(charset))
                }
                logger.log("response Code >> " + response.code())
                logger.log("response SuccessFul >> " + response.isSuccessful)
                endMessage += " (" + buffer.size() + "-byte body)"
            }
            logger.log(endMessage)
        }
        return response
    }

    /**
     * Logging Builder
     */
    class Builder {
        var isDebug = false
        var level: Level
            private set
        var logger: Logger? = null
        fun setLevel(level: Level): Builder {
            this.level = level
            return this
        }

        fun loggable(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        fun logger(logger: Logger?): Builder {
            this.logger = logger
            return this
        }

        fun build(): LoggingInterceptor {
            return LoggingInterceptor(this)
        }

        init {
            level = Level.BODY
        }
    }

    companion object {
        private val UTF8 = StandardCharsets.UTF_8
        private fun protocol(protocol: Protocol): String {
            return if (protocol == Protocol.HTTP_1_0) "HTTP/1.0" else "HTTP/1.1"
        }
    }

    /**
     * Instantiates a new Http interceptor.
     */
    init {
        isDebug = mBuilder.isDebug
        logger = mBuilder.logger
    }
}