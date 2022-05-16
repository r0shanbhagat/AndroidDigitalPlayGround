package com.digital.playground.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digital.playground.core.BaseViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
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
    private val repository: com.digital.playground.contract.Repository
) : BaseViewModel() {

    private val _viewState: MutableLiveData<ViewState> by lazy {
        MutableLiveData<ViewState>()
    }

    private var moviesList: ArrayList<MovieModel> = ArrayList()


    val viewState: LiveData<ViewState> = _viewState


    /**
     * Set state intent
     *
     * @param mainStateEvent
     */
    fun setStateIntent(mainStateEvent: MovieStateEvent) {
        when (mainStateEvent) {
            is MovieStateEvent.GetMoviesList -> {
                getSearchResultData(mainStateEvent.data.toString())
            }

            is MovieStateEvent.None -> {
                //TODO will work on New flow
            }
        }
    }

    /*
     * getBlogContent return the movie parsed data using flow that continuously emit the value
     */
    private fun getSearchResultData(searchTitle: String) {

        viewModelScope.launch {

            moviesList.clear()

            repository
                .getSearchResultData(searchTitle, 1)
                .onStart {
                    _viewState.postValue(ViewState.Loading)
                }
                .catch { exception ->
                    _viewState.postValue(ViewState.Failure(exception))
                }
                .collect {
                    moviesList.addAll(it)
                    moviesList.sortByDescending {
                        it.year
                    }

                    _viewState.postValue(ViewState.Success(moviesList))
                }

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
    data class GetMoviesList(val data: Any?) : MovieStateEvent()

    /**
     * None
     *
     * @constructor Create empty None
     */
    object None : MovieStateEvent()
}