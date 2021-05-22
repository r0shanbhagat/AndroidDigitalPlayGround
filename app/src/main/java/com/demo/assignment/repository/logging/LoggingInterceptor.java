package com.demo.assignment.repository.logging;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private final boolean isDebug;
    private final Logger logger;
    private final LoggingInterceptor.Builder mBuilder;

    /**
     * Instantiates a new Http interceptor.
     */
    public LoggingInterceptor(LoggingInterceptor.Builder builder) {
        this.mBuilder = builder;
        this.isDebug = builder.isDebug;
        this.logger = builder.logger;
    }


    private static String protocol(Protocol protocol) {
        return protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        /*
         *Log Disabled in the case of Level.NONE || Release build
         */
        if (!isDebug || mBuilder.getLevel() == Level.NONE) {
            return chain.proceed(request);
        }

        boolean logBody = mBuilder.getLevel() == Level.BODY;
        boolean logHeaders = logBody || mBuilder.getLevel() == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        long startNsRequest = System.nanoTime();
        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage =
                "--> REQUEST START " + request.url() + ' ' + request.method() + ' ' /*+ requestPath(request.httpUrl()) + ' '*/ + protocol(protocol);
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.log(requestStartMessage);

        if (logHeaders) {
            Headers headers = request.headers();
            if (null != headers && headers.size() > 0) {
                logger.log("Headers START -->");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    logger.log(headers.name(i) + ": " + headers.value(i));
                }
                logger.log("<-- Headers END");
            }

            String endMessage = "--> REQUEST END ";//+ request.method();
            if (logBody && hasRequestBody) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    contentType.charset(UTF8);
                }

                logger.log("");

                long tookMsRequest = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNsRequest);
                endMessage += " (" + requestBody.contentLength() + "-byte body) (" + tookMsRequest + "ms)";
            }
            logger.log(endMessage);
        }

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        logger.log("<-- RESPONSE START " + protocol(response.protocol()) + ' ' + response.code() + ' '
                + response.message() + " (" + tookMs + "ms"
                + (!logHeaders ? ", " + responseBody.contentLength() + "-byte body" : "") + ')');

        if (logHeaders) {
            Headers headers = response.headers();
            if (null != headers) {
                logger.log("Headers START -->");
                for (int i = 0, count = headers.size(); i < count; i++) {
                    logger.log(headers.name(i) + ": " + headers.value(i));
                }
                logger.log("<-- Headers END");
            }

            String endMessage = "<-- RESPONSE END ";
            if (logBody) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (responseBody.contentLength() != 0) {
                    logger.log("\n");
                    logger.log(buffer.clone().readString(charset));
                }

                logger.log("response Code >> " + response.code());
                logger.log("response SuccessFul >> " + response.isSuccessful());

                endMessage += " (" + buffer.size() + "-byte body)";
            }
            logger.log(endMessage);
        }
        return response;
    }

    /**
     * Logging Builder
     */
    public static class Builder {
        private boolean isDebug;
        private Level level;
        private Logger logger;

        public Builder() {
            this.level = Level.BODY;
        }

        Level getLevel() {
            return this.level;
        }

        public LoggingInterceptor.Builder setLevel(Level level) {
            this.level = level;
            return this;
        }

        public LoggingInterceptor.Builder loggable(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public LoggingInterceptor.Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public LoggingInterceptor build() {
            return new LoggingInterceptor(this);
        }
    }

}

