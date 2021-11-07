package com.digital.playground.repository.logging

import com.digital.playground.util.AppUtils.showLog

/**
 * The interface Logger.
 */
interface Logger {
    /**
     * Log.
     *
     * @param message the message
     */
    fun log(message: String)


    companion object {
        /**
         * A [Logger] defaults output appropriate for the current platform.
         */
        val DEFAULT: Logger = object : Logger {
            override fun log(message: String) {
                showLog(LoggingInterceptor::class.simpleName, message)
            }
        }
    }
}