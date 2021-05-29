package com.demo.assignment.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.demo.assignment.core.BaseViewModel
import com.demo.assignment.repository.model.LoginModel
import javax.inject.Inject


class LoginViewModel @Inject constructor(loginModel: LoginModel) : BaseViewModel() {
    val loginLiveData: MutableLiveData<LoginModel>

    /**
     *
     */
    init {
        loginLiveData = MutableLiveData()
        loginLiveData.postValue(loginModel)
    }

}