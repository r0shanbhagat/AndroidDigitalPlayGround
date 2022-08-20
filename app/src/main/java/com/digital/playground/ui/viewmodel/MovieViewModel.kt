package com.digital.playground.ui.viewmodel

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digital.playground.contract.UseCase
import com.digital.playground.core.BaseViewModel
import com.digital.playground.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Details Movie parse view model : Viewmodel to handle all the business logic
 * @Author Roshan Bhagat
 * @property movieContentUseCase: A bridge object to communicate b/w your repo and data source
 * @constructor
 */
@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieContentUseCase: UseCase
) : BaseViewModel() {

    companion object {
        const val EMPTY_DATA = 1
        const val ERROR = 2
        const val LOADING = 0
        const val LISTING_ITEM = 1
    }


    private val _dataState: MutableLiveData<DataState> by lazy {
        MutableLiveData<DataState>()
    }

    val dataState: LiveData<DataState> = _dataState
    val errorState: ObservableInt = ObservableInt(LOADING)


    /**
     * Set state intent
     *
     * @param mainStateEvent
     */
    fun setStateIntent(mainStateEvent: MovieStateEvent) {
        when (mainStateEvent) {
            is MovieStateEvent.GetMoviesList -> {
                getMovieList()
            }

            is MovieStateEvent.None -> {
                //TODO will work on New flow
            }
        }
    }

    /*
     * getBlogContent return the movie parsed data using flow that continuously emit the value
     */
    private fun getMovieList() {
        viewModelScope.launch {
            movieContentUseCase.getMovieList()
                .onEach { flowData ->
                    _dataState.value = flowData
                }
                .launchIn(viewModelScope)
        }
    }

}


/**
 * Movie state event.
 *
 * @constructor Create empty constructor for movie state event
 */
sealed class MovieStateEvent {
    /**
     * Get movie list
     *
     * @constructor
     */
    object GetMoviesList : MovieStateEvent()

    /**
     * None
     *
     * @constructor Create empty None
     */
    object None : MovieStateEvent()
}