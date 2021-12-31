package com.digital.playground.ui.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digital.playground.core.BaseViewModel
import com.digital.playground.repository.MovieRepository
import com.digital.playground.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    companion object {
        const val EMPTY_DATA = 1
        const val ERROR = 2
        const val LOADING = 0
    }

    val dataState: MutableLiveData<DataState> by lazy {
        MutableLiveData<DataState>()
    }

    val errorState: ObservableInt = ObservableInt(LOADING)

    fun setStateEvent(mainStateEvent: MovieStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MovieStateEvent.GetMoviesList -> {
                    repository.getMovies()
                        .onEach { flowData ->
                            dataState.value = flowData
                        }
                        .launchIn(viewModelScope)
                }

                is MovieStateEvent.None -> {
                    // No action
                }
            }
        }
    }

    fun resetLiveData() {
        dataState.postValue(null)
    }
}


sealed class MovieStateEvent {
    object GetMoviesList : MovieStateEvent()
    object None : MovieStateEvent()
}