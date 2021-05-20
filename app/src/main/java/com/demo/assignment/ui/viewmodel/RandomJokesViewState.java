package com.demo.assignment.ui.viewmodel;


import com.demo.assignment.core.BaseViewState;

public class RandomJokesViewState extends BaseViewState<Object> {
    public static final RandomJokesViewState ERROR_STATE = new RandomJokesViewState(null, State.FAILED.value, new Throwable());
    public static final RandomJokesViewState LOADING_STATE = new RandomJokesViewState(null, State.LOADING.value, null);
    public static final RandomJokesViewState SUCCESS_STATE = new RandomJokesViewState(null, State.SUCCESS.value, null);

    /**
     *
     * @param data :Data
     * @param currentState :currentState
     * @param error :Exception
     */
    public RandomJokesViewState(Object data, int currentState, Throwable error) {
        this.data = data;
        this.currentState = currentState;
        this.error = error;
    }
}
