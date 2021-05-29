package com.demo.assignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.assignment.repository.model.LoginModel


class LoginViewModel constructor(loginModel: LoginModel) : ViewModel() {
    val loginLiveData: MutableLiveData<LoginModel>

    /**
     *
     */
    init {
        loginLiveData = MutableLiveData()
        loginLiveData.postValue(loginModel)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val loginModel: LoginModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(loginModel) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}