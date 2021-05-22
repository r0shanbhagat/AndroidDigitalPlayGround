package com.demo.assignment.repository.logging;

import com.demo.assignment.util.AppUtils;

/**
 * The interface Logger.
 */
public interface Logger {
    /**
     * A {@link Logger} defaults output appropriate for the current platform.
     */
    Logger DEFAULT = message ->
            AppUtils.showLog(LoggingInterceptor.class.getSimpleName(), message);

    /**
     * Log.
     *
     * @param message the message
     */
    void log(String message);
}