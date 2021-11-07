package com.digital.playground.ui.viewmodel;


import com.digital.playground.core.ViewState;

public class RandomJokesViewState extends ViewState {
    public static final ViewState ERROR_STATE = new RandomJokesViewState(FAILED);
    public static final ViewState LOADING_STATE = new RandomJokesViewState(LOADING);
    public static final ViewState SUCCESS_STATE = new RandomJokesViewState(SUCCESS);

    public RandomJokesViewState(@State int currentState) {
        super(currentState);
    }
}
