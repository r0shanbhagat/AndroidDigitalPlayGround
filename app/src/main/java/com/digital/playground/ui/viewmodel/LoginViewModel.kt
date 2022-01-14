package com.digital.playground.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.digital.playground.core.BaseViewModel
import com.digital.playground.repository.model.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Details LoginViewModel:
 * @Author Roshan Bhagat
 */
@HiltViewModel
class LoginViewModel @Inject constructor(loginModel: LoginModel) : BaseViewModel() {
    val loginLiveData: MutableLiveData<LoginModel> = MutableLiveData()

    /**
     *
     */
    init {
        loginLiveData.postValue(loginModel)
    }
}