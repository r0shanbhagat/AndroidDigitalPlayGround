package com.digital.playground.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.digital.playground.contract.Repository
import com.digital.playground.core.BaseViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Details Movie parse view model : Viewmodel to handle all the business logic
 * @Author Roshan Bhagat
 * StateFlow :https://developer.android.com/topic/architecture/ui-layer#views
 * https://medium.com/androiddevelopers/coroutines-patterns-for-work-that-shouldnt-be-cancelled-e26c40f142ad
 * https://developer.android.com/kotlin/coroutines/coroutines-best-practices#viewmodel-coroutines
 *
 * @constructor
 */
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private var fetchJob: Job? = null

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
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
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