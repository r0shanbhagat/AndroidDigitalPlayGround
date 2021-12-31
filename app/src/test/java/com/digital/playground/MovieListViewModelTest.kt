package com.digital.playground

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.digital.playground.repository.MovieRepository
import com.digital.playground.repository.model.MovieModel
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieListViewModelTest {

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var viewStateObserver: Observer<DataState>

    @Mock
    private lateinit var mockMovieResponse: List<MovieModel>

    @Mock
    private lateinit var mockException: Exception

    @Mock
    private lateinit var repository: MovieRepository

    private val fakeSuccessFlow = flow {
        emit(DataState.Success(mockMovieResponse))
    }

    private val fakeFailureFlow = flow {
        emit(DataState.Error(mockException))
    }

    private val viewModel: MovieListViewModel by lazy {
        MovieListViewModel(repository)
    }

    @Before
    fun setup() {
        viewModel.dataState.observeForever(viewStateObserver)
    }

    @Test
    fun testApiFetchDataSuccess() {
        runBlockingTest {
            Mockito.`when`(repository.getMovies()).thenReturn(fakeSuccessFlow)
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)

            viewStateObserver.onChanged(DataState.Loading)
            viewStateObserver.onChanged(DataState.Success(mockMovieResponse))
        }
    }

    @Test
    fun `when load movie list service throws network failure then ViewState renders failure`() {
        runBlockingTest {
            Mockito.`when`(repository.getMovies()).thenReturn(fakeFailureFlow)
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
            viewStateObserver.onChanged(DataState.Loading)
            viewStateObserver.onChanged(DataState.Error(mockException))
        }
    }

    @After
    fun tearDown() {
        viewModel.resetLiveData()
    }


}