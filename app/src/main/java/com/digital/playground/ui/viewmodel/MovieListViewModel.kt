package com.digital.playground.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.digital.playground.core.BaseObservable
import com.digital.playground.core.BaseViewModel
import com.digital.playground.repository.ApiService
import com.digital.playground.repository.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val apiService: ApiService) : BaseViewModel() {
    val movieLiveData: MutableLiveData<MovieViewState>
    private var mDisposable: CompositeDisposable?

    init {
        mDisposable = CompositeDisposable()
        movieLiveData = MutableLiveData()
    }

    /*
     * Fetch Random Jokes List from Server
     */
    fun getMoviesList() {
        onLoading()
        val observable: Observable<List<MovieModel>> = apiService.getAllMovies()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
        observable.subscribe(object : BaseObservable<List<MovieModel>>() {
            override fun success(response: List<MovieModel>) {
                onSuccess(response)
            }

            override fun failure(t: Throwable) {
                onFailure(t)
            }

            override fun onSubscribe(dispo: Disposable) {
                mDisposable!!.add(dispo)
            }
        })
    }

    /**
     * Show Loading State
     */
    private fun onLoading() {
        movieLiveData.postValue(MovieViewState.LOADING_STATE)
    }

    /*
     * Successfully fetched the Jokes from Server
     * @param movieList
     */
    private fun onSuccess(movieList: List<MovieModel>) {
        MovieViewState.SUCCESS_STATE.data = movieList
        movieLiveData.postValue(MovieViewState.SUCCESS_STATE)
    }

    /**
     * Failure while fetching the jokes from server
     *
     * @param error :Error
     */
    private fun onFailure(error: Throwable) {
        MovieViewState.ERROR_STATE.error = error
        movieLiveData.postValue(MovieViewState.ERROR_STATE)
    }

    override fun onCleared() {
        super.onCleared()
        if (null != mDisposable) {
            mDisposable?.clear()
            mDisposable = null
        }
    }

    fun resetLiveData() {
        movieLiveData.postValue(null)
    }
}