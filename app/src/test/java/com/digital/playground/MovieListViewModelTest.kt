package com.digital.playground

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import com.digital.playground.core.ViewState
import com.digital.playground.repository.ApiService
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieViewState
import io.reactivex.rxjava3.core.Observable
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(JUnit4::class)
class MovieListViewModelTest {

    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var instantExecutorRule1 = RxImmediateSchedulerRule()

    @Mock
    lateinit var apiClient: ApiService

    @Mock
    lateinit var observer: Observer<ViewState>

    @Mock
    private lateinit var viewModel: MovieListViewModel

    @Mock
    lateinit var owner: ViewModelStoreOwner

    @Before
    @Throws(Exception::class)
    fun setUp() {

        // viewModel = ViewModelProvider(viewModelStore).get(MovieListViewModel::class.java)
        // val factory = BaseViewModel.Factory(apiClient, "Rajvi", "Bhatia")

        viewModel.movieLiveData.observeForever(observer)
    }

    @Test
    fun testNull() {
        Mockito.`when`(apiClient.getAllMovies()).thenReturn(null)
        Assert.assertNotNull(viewModel.movieLiveData)
        Assert.assertTrue(viewModel.movieLiveData.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        Mockito.`when`(apiClient.getAllMovies())
            .thenReturn(Observable.just(mutableListOf()))
        viewModel.getMoviesList()
        Mockito.verify(observer).onChanged(MovieViewState.LOADING_STATE)
        Mockito.verify(observer).onChanged(MovieViewState.SUCCESS_STATE)
    }

    @Test
    fun testApiFetchDataError() {
        Mockito.`when`(apiClient.getAllMovies())
            .thenReturn(Observable.error(Throwable("Api error")))
        viewModel.getMoviesList()
        Mockito.verify(observer).onChanged(MovieViewState.LOADING_STATE)
        Mockito.verify(observer).onChanged(MovieViewState.ERROR_STATE)
    }

    @After
    fun tearDown() {
        viewModel.resetLiveData()
    }
}