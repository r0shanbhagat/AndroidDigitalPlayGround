package com.playground.movieapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.playground.movieapp.contract.Repository
import com.playground.movieapp.core.BaseViewModel
import com.playground.movieapp.ui.adapter.MovieModel
import com.playground.movieapp.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Details Movie parse view model : Viewmodel to handle all the business logic
 * @Author Roshan Bhagat
 * StateFlow :https://developer.android.com/topic/architecture/ui-layer#views
 * @constructor
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<ViewState> by lazy {
        MutableStateFlow(ViewState())
    }
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    private val moviesList: MutableList<MovieModel> by lazy {
        mutableListOf()
    }

    init {
        setStateIntent(MovieStateEvent.DiscoverMovie)
    }

    /**
     * Set state intent
     *
     * @param mainStateEvent
     */
    internal fun setStateIntent(mainStateEvent: MovieStateEvent) {
        when (mainStateEvent) {
            is MovieStateEvent.DiscoverMovie -> {
                discoverMovies()
            }

            is MovieStateEvent.None -> {
                //TODO will work on New flow
            }
        }
    }

    /*
     * getBlogContent return the movie parsed data using flow that continuously emit the value
     */
    private fun discoverMovies() {
        viewModelScope.launch {
            moviesList.clear()
            repository
                .discoverMovies(1)
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
    object DiscoverMovie : MovieStateEvent()

    /**
     * None
     *
     * @constructor Create empty None
     */
    object None : MovieStateEvent()
}