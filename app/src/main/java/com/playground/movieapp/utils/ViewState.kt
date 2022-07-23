package com.playground.movieapp.utils

/**
 * @Details DataState: It holds the state of your app service data.This way we can write
 * the business logic in more clear way
 * @Author Roshan Bhagat
 */
open class ViewState {

    /**
     * Success
     *
     * @property data
     * @constructor Create Success
     */
    data class Success(val data: Any?) : ViewState()

    /**
     * Error
     *
     * @property throwable
     * @constructor Create  Error
     */
    data class Failure(val throwable: Throwable) : ViewState()

    /**
     * Loading
     *
     * @constructor Create empty Loading
     */
    object Loading : ViewState()
}