package com.digital.playground.ui.viewmodel

import com.digital.playground.core.ViewState

class MovieViewState(@State currentState: Int) : ViewState(currentState) {
    companion object {
        val ERROR_STATE: MovieViewState = MovieViewState(FAILED)
        val LOADING_STATE: MovieViewState = MovieViewState(LOADING)
        val SUCCESS_STATE: MovieViewState = MovieViewState(SUCCESS)
    }
}