package com.digital.playground.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.digital.playground.contract.Repository
import com.digital.playground.core.BaseViewModel
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
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _movieResult = MutableLiveData<ViewState>()
    val movieResult: LiveData<ViewState> = _movieResult

    /**
     * Process the [Bundle] argument from the list fragment to process the photo details
     * @param args [Bundle] object containing parcelized [PhotoDetails] instance
     * @since 1.0
     */
    /* fun processPhotoDetailsArgument(@NonNull args: Bundle) {
         flow {
             photoDetails?.let {
                 emit(ViewState.RenderSuccess(it))
             } ?: run {
                 emit(ViewState.RenderFailure(Exception("No Photo Details found")))

             }
         }.asLiveData()
     }
 */



    fun getMovieDetailsData(imdbId: String) {

        viewModelScope.launch {

            repository.getMovieDetailsData(imdbId)
                .onStart {
                    _movieResult.postValue(ViewState.Loading)
                }
                .catch {
                    _movieResult.postValue(ViewState.Failure(it))

                }
                .collect {
                    _movieResult.postValue(ViewState.Success(it))
                }


        }

    }


}

