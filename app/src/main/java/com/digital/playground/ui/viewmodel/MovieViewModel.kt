package com.digital.playground.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.digital.playground.contract.Repository
import com.digital.playground.core.BaseViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.utils.ViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Details Movie parse view model : Viewmodel to handle all the business logic
 * @Author Roshan Bhagat
 * StateFlow :https://developer.android.com/topic/architecture/ui-layer#views
 * @constructor
 */
class MovieViewModel(
    private val repository: Repository,
    searchText: String
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<ViewState> by lazy {
        MutableStateFlow(ViewState())
    }
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private var moviesList: ArrayList<MovieModel> = ArrayList()

    init {
        setStateIntent(MovieStateEvent.GetMoviesList(searchText))
    }

    /**
     * Set state intent
     *
     * @param mainStateEvent
     */
    private fun setStateIntent(mainStateEvent: MovieStateEvent) {
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
                    _uiState.emit(ViewState.Loading)
                }
                .catch { exception ->
                    _uiState.emit(ViewState.Failure(exception))
                }
                .collect { it ->
                    moviesList.addAll(it)
                    moviesList.sortByDescending {
                        it.year
                    }

                    _uiState.emit(ViewState.Success(moviesList))
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