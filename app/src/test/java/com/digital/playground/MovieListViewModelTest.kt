package com.digital.playground

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import com.digital.playground.network.ApiService
import com.digital.playground.repository.model.Movie
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.util.DataState
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
    lateinit var observer: Observer<DataState<List<Movie>>>

    @Mock
    private lateinit var viewModel: MovieListViewModel

    @Mock
    lateinit var owner: ViewModelStoreOwner

    @Before
    @Throws(Exception::class)
    fun setUp() {

        // viewModel = ViewModelProvider(viewModelStore).get(MovieListViewModel::class.java)
        // val factory = BaseViewModel.Factory(apiClient, "Rajvi", "Bhatia")

        viewModel.dataState.observeForever(observer)
    }

    @Test
    suspend fun testNull() {
        Mockito.`when`(apiClient.getAllMovies()).thenReturn(null)
        Assert.assertNotNull(viewModel.dataState)
        Assert.assertTrue(viewModel.dataState.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        Mockito.`when`(apiClient.getAllMovies())
            .thenReturn(Observable.just(mutableListOf()))
        viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        Mockito.verify(observer).onChanged(DataState.Loading)
        Mockito.verify(observer).onChanged(DataState.Success(movieList))
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