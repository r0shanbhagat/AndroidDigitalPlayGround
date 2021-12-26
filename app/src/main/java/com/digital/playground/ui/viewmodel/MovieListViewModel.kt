package com.digital.playground.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digital.playground.core.BaseViewModel
import com.digital.playground.repository.MovieRepository
import com.digital.playground.repository.model.Movie
import com.thecode.dagger_hilt_mvvm.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Movie>>> = MutableLiveData()

    val dataState: MutableLiveData<DataState<List<Movie>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MovieStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MovieStateEvent.GetMoviesList -> {
                    repository.getMovies()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MovieStateEvent.None -> {
                    // No action
                }
            }
        }
    }
}


sealed class MovieStateEvent {
    object GetMoviesList : MovieStateEvent()
    object None : MovieStateEvent()
}