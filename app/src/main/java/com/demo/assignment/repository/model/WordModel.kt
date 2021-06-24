package com.demo.assignment.repository.model

import android.text.TextUtils
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.demo.assignment.BR

class WordModel : BaseObservable() {

    @get:Bindable
    var word: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.word)
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
        isSubmitEnable = !TextUtils.isEmpty(word)
                && !TextUtils.isEmpty(text)
    }


    fun onClear() {
        word = ""
        isSubmitEnable = false
    }


}