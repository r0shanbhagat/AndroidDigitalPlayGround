package com.digital.playground

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.digital.playground.core.ViewState
import com.digital.playground.repository.ApiService
import com.digital.playground.repository.model.RandomJokesModel
import com.digital.playground.ui.viewmodel.RandomJokesViewModel
import com.digital.playground.ui.viewmodel.RandomJokesViewState
import io.reactivex.rxjava3.core.Observable
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.*

@RunWith(JUnit4::class)
class RandomJokesViewModelTest {

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
    private lateinit var viewModel: RandomJokesViewModel
    private lateinit var requestMap: HashMap<String, String>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val factory = RandomJokesViewModel.Factory(apiClient, "Rajvi", "Bhatia")
        viewModel = ViewModelProvider(ViewModelStore(), factory).get(
            RandomJokesViewModel::class.java
        )
        viewModel.jokesData.observeForever(observer)
        requestMap = HashMap()
        requestMap["firstName"] = "Rajvi"
        requestMap["lastName"] = "Bhatia"
    }

    @Test
    fun testNull() {
        Mockito.`when`(apiClient.getJokesList(requestMap)).thenReturn(null)
        Assert.assertNotNull(viewModel.jokesData)
        Assert.assertTrue(viewModel.jokesData.hasObservers())
    }

    @Test
    fun testApiFetchDataSuccess() {
        // Mock API response
        Mockito.`when`(apiClient.getJokesList(requestMap))
            .thenReturn(Observable.just(RandomJokesModel()))
        viewModel.fetchJokesList()
        Mockito.verify(observer).onChanged(RandomJokesViewState.LOADING_STATE)
        Mockito.verify(observer).onChanged(RandomJokesViewState.SUCCESS_STATE)
    }

    @Test
    fun testApiFetchDataError() {
        Mockito.`when`(apiClient.getJokesList(requestMap))
            .thenReturn(Observable.error(Throwable("Api error")))
        viewModel.fetchJokesList()
        Mockito.verify(observer).onChanged(RandomJokesViewState.LOADING_STATE)
        Mockito.verify(observer).onChanged(RandomJokesViewState.ERROR_STATE)
    }

    @After
    fun tearDown() {
        viewModel.resetLiveData()
    }
}