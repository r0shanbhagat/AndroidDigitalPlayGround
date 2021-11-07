package com.digital.playground.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.digital.playground.core.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaygroundViewModel : BaseViewModel() {
    private var dataLoaded: Boolean = false

    fun isSplashTimeout(): Boolean {
        viewModelScope.launch {
            delay(800)
            dataLoaded = true
        }
        return dataLoaded
    }
}
