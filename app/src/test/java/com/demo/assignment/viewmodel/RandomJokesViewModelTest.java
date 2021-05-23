package com.demo.assignment.viewmodel;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;

import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.model.RandomJokesModel;
import com.demo.assignment.ui.viewmodel.RandomJokesViewModel;
import com.demo.assignment.ui.viewmodel.RandomJokesViewState;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@RunWith(JUnit4.class)
//Ref:https://medium.com/@iamanbansal/testing-viewmodel-livedata-4a62f34e7c26
@RunWith(MockitoJUnitRunner.class)
public class RandomJokesViewModelTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    ApiService apiService;
    @Mock
    Observer<RandomJokesViewState> observer;

    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;
    Map<String, String> testData;
    @Mock
    private RandomJokesViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        SavedStateHandle savedStateHandle = new SavedStateHandle();
        savedStateHandle.set("args_first_name", "Test");
        savedStateHandle.set("args_last_name", "Data");
        viewModel = new RandomJokesViewModel(savedStateHandle, apiService);
        viewModel.getJokesData().observeForever(observer);
        testData = new HashMap<>();
        testData.put("firstName", "Demo");
        testData.put("lastName", "Test");
    }

    @Test
    public void testNull() {
        when(apiService.getJokesList(testData)).thenReturn(null);
        assertNotNull(viewModel.getJokesData());
        assertTrue(viewModel.getJokesData().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        // Mock API response
        when(apiService.getJokesList(testData)).thenReturn(Observable.just(new RandomJokesModel()));
        viewModel.fetchJokesList();
        verify(observer).onChanged(RandomJokesViewState.LOADING_STATE);
        verify(observer).onChanged(RandomJokesViewState.SUCCESS_STATE);
    }

    @Test
    public void testApiFetchDataError() {
        when(apiService.getJokesList(testData)).thenReturn(Observable.error(new Throwable("Api error")));
        viewModel.fetchJokesList();
        verify(observer).onChanged(RandomJokesViewState.LOADING_STATE);
        verify(observer).onChanged(RandomJokesViewState.ERROR_STATE);
    }

    @After
    public void tearDown() throws Exception {
        apiService = null;
        viewModel = null;
    }
}