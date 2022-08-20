package com.digital.playground.utils

/**
 * @Details DataState: It holds the state of your app service data.This way we can write
 * the business logic in more clear way
 * @Author Roshan Bhagat
 */
sealed class DataState {

    /**
     * Success
     *
     * @property data
     * @constructor Create Success
     */
    data class Success(val data: Any?) : DataState()

    /**
     * Error
     *
     * @property exception
     * @constructor Create  Error
     */
    data class Error(val exception: Throwable) : DataState()

    /**
     * Loading
     *
     * @constructor Create empty Loading
     */
    object Loading : DataState()
}