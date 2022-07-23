package com.playground.movieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.playground.movieapp.data.MoviesRepository
import com.playground.movieapp.data.dto.MovieDetailModel
import com.playground.movieapp.data.mapper.MovieMapper
import com.playground.movieapp.ui.viewmodel.DetailViewModel
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
class MovieDetailViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MoviesRepository

    @Mock
    private lateinit var movieDetailModel: MovieDetailModel

    private val viewModel: DetailViewModel by lazy {
        DetailViewModel(repository)
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

            Mockito.`when`(repository.getMovieDetail(101)).thenReturn(flow {
                emit(movieDetailModel)
            })
            viewModel.getMovieDetailsData(101)
            TestCase.assertEquals(event[1], ViewState.Loading)
            TestCase.assertEquals(event[2], ViewState.Success(movieDetailModel))

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
            viewModel.getMovieDetailsData(101)
            TestCase.assertEquals(event[1], ViewState.Loading)
            TestCase.assertTrue(event[2] is ViewState.Failure)

        }
    }
}
