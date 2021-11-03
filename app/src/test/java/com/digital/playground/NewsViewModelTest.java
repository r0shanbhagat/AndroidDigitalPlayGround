package com.digital.playground;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.digital.playground.core.ViewState;
import com.digital.playground.repository.ApiService;
import com.digital.playground.repository.model.RandomJokesModel;
import com.digital.playground.ui.viewmodel.RandomJokesViewModel;
import com.digital.playground.ui.viewmodel.RandomJokesViewState;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;


@RunWith(JUnit4.class)
public class NewsViewModelTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public RxImmediateSchedulerRule instantExecutorRule1 = new RxImmediateSchedulerRule();

    @Mock
    ApiService apiClient;
    @Mock
    Observer<ViewState> observer;

    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    private RandomJokesViewModel viewModel;
    private Map<String, String> requestMap;

    @Before
    public void setUp() {
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        RandomJokesViewModel.Factory factory =
                new RandomJokesViewModel.Factory(apiClient, "Rajvi", "Bhatia");
        viewModel = new ViewModelProvider(new ViewModelStore(), factory).get(RandomJokesViewModel.class);
        viewModel.getJokesData().observeForever(observer);
        requestMap = new HashMap<>();
        requestMap.put("firstName", "Rajvi");
        requestMap.put("lastName", "Bhatia");
    }

    @Test
    public void testNull() {
        when(apiClient.getJokesList(requestMap)).thenReturn(null);
        assertNotNull(viewModel.getJokesData());
        assertTrue(viewModel.getJokesData().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        when(apiClient.getJokesList(requestMap)).thenReturn(Observable.just(new RandomJokesModel()));
        viewModel.fetchJokesList();
        verify(observer).onChanged(RandomJokesViewState.LOADING_STATE);
        verify(observer).onChanged(RandomJokesViewState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(apiClient.getJokesList(requestMap)).thenReturn(Observable.error(new Throwable("Api error")));
        viewModel.fetchJokesList();
        verify(observer).onChanged(RandomJokesViewState.LOADING_STATE);
        verify(observer).onChanged(RandomJokesViewState.ERROR_STATE);
    }

    @After
    public void tearDown() {
        viewModel.resetLiveData();
    }
}
