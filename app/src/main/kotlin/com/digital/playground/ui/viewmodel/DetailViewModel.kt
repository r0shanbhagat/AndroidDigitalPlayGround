package com.digital.playground.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.digital.playground.contract.Repository
import com.digital.playground.core.BaseViewModel
import com.digital.playground.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
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
 * @constructor
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {
    private val _uiState: MutableStateFlow<ViewState> by lazy {
        MutableStateFlow(ViewState())
    }
    val uiState: StateFlow<ViewState> = _uiState.asStateFlow()

    /**
     * Get movie details data.
     *
     * @param movieId Movie id
     */
    internal fun getMovieDetailsData(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetail(movieId)
                .onStart {
                    _uiState.emit(ViewState.Loading)
                }
                .catch {
                    _uiState.emit(ViewState.Failure(it))
                }
                .collect {
                    _uiState.emit(ViewState.Success(it))
                }
        }
    }
}

