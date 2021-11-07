package com.digital.playground.core

import androidx.annotation.IntDef

/**
 * Abstract BaseViewState :ViewState—State Reducer
 * *STATE_LOADING for the initial, loading state
 * *STATE_SUCCESS for the Success state
 * *STATE_FAILURE for the Failure state
 * *STATE_EMPTY for Empty Response from Server
 * *STATE_LOAD_MORE for Paging to show a loader to fetch More
 */
abstract class ViewState {
    var error: Throwable? = null
    var currentState: Int
    var data: Any? = null

    constructor(@State currentState: Int) {
        this.currentState = currentState
    }

    constructor(@State currentState: Int, data: Any?) {
        this.currentState = currentState
        this.data = data
    }

    @IntDef(LOADING, SUCCESS, FAILED, EMPTY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State

    companion object {
        const val LOADING = 0
        const val SUCCESS = 1
        const val FAILED = -1
        const val EMPTY = 2
    }
}