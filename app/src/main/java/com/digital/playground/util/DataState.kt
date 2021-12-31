package com.digital.playground.util

sealed class DataState {

    data class Success(val data: Any) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading : DataState()
}