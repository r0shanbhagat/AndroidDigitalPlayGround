package com.digital.playground.repository.model

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.digital.playground.BR

class LoginModel : BaseObservable() {

    @get:Bindable
    var firstName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }

    @get:Bindable
    var lastName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }


    @get:Bindable
    var isSubmitEnable: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.submitEnable)
        }

    @Suppress("UNUSED_PARAMETER")
    fun onTextChanged(text: CharSequence, start: Int, before: Int,
                      count: Int) {
        isSubmitEnable = (!TextUtils.isEmpty(firstName)
                && !TextUtils.isEmpty(text)
                && !TextUtils.isEmpty(lastName))
    }


}