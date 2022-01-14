package com.digital.playground.util
/**
 * @Details DataState: It holds the state of your app service data.This way we can write
 * the business logic in more clear way
 * @Author Roshan Bhagat
 */
sealed class DataState {

    data class Success(val data: Any) : DataState()
    data class Error(val exception: Exception) : DataState()
    object Loading : DataState()
}