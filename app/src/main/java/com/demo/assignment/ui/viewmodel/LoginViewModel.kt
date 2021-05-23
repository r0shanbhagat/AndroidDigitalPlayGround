package com.demo.assignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.assignment.repository.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(loginModel: LoginModel) : ViewModel() {
    val loginLiveData: MutableLiveData<LoginModel>

    /**
     *
     */
    init {
        loginLiveData = MutableLiveData()
        loginLiveData.postValue(loginModel)
    }
}