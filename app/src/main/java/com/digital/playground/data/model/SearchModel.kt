package com.digital.playground.data.model

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.digital.playground.BR

/**
 * @Details LoginModel
 * @Author Roshan Bhagat
 */
class SearchModel : BaseObservable() {

    @get:Bindable
    var searchText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchText)

        }


    @get:Bindable
    var isSubmitEnable: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.submitEnable)
        }

    fun onTextChanged(searchText: CharSequence) {
        isSubmitEnable = !TextUtils.isEmpty(searchText) && searchText.length > 3
    }


}