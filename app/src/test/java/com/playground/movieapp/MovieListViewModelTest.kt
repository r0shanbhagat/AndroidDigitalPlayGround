package com.playground.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.playground.movieapp.data.MoviesRepository
import com.playground.movieapp.data.mapper.MovieMapper
import com.playground.movieapp.ui.adapter.MovieModel
import com.playground.movieapp.ui.viewmodel.MovieListViewModel
import com.playground.movieapp.ui.viewmodel.MovieStateEvent
import com.playground.movieapp.utils.ViewState
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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

    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MoviesRepository

    private val viewModel: MovieListViewModel by lazy {
        MovieListViewModel(repository)
    }

    private val mapper: MovieMapper by lazy {
        MovieMapper()
    }

    @Before
    fun setup() {

    }

    @Test
    fun testApiFetchDataSuccess() {
        runTest {
            val event = mutableListOf<ViewState>()
            viewModel.uiState
                .onEach {
                    event.add(it)
                }
                .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

            val mockList = mutableListOf(
                MovieModel(
                    1,
                    "Testing Title",
                    "This is testing body",
                    "/image",
                    "2000",
                    "5"
                )
            )
            Mockito.`when`(repository.discoverMovies(1)).thenReturn(flow {
                emit(mockList)
            })
            viewModel.setStateIntent(MovieStateEvent.DiscoverMovie)
            TestCase.assertEquals(event[1], ViewState.Loading)
            TestCase.assertEquals(event[2], ViewState.Success(mockList))

        }
    }

    @Test
    fun `when load movie list service throws network failure then ViewState renders failure`() {
        runTest {
            val event = mutableListOf<ViewState>()
            viewModel.uiState
                .onEach {
                    event.add(it)
                }
                .launchIn(CoroutineScope(UnconfinedTestDispatcher(testScheduler)))

            val mockList = mutableListOf<MovieModel>()
            Mockito.`when`(repository.discoverMovies(1)).thenReturn(flow {
                mockList[0]
                emit(mockList)
            })
            viewModel.setStateIntent(MovieStateEvent.DiscoverMovie)
            TestCase.assertEquals(event[1], ViewState.Loading)
            TestCase.assertTrue(event[2] is ViewState.Failure)

        }
    }
}
