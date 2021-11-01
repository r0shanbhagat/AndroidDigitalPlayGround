package com.digital.playground.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.digital.playground.core.BaseViewModel
import com.digital.playground.repository.model.LoginModel
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