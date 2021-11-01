package com.digital.playground.ui.viewmodel;


import com.digital.playground.core.BaseViewState;
import com.digital.playground.repository.model.RandomJokesModel;

public class RandomJokesViewState extends BaseViewState<RandomJokesModel> {
    public static final RandomJokesViewState ERROR_STATE = new RandomJokesViewState(null, State.FAILED.value, new Throwable());
    public static final RandomJokesViewState LOADING_STATE = new RandomJokesViewState(null, State.LOADING.value, null);
    public static final RandomJokesViewState SUCCESS_STATE = new RandomJokesViewState(new RandomJokesModel(), State.SUCCESS.value, null);

    /**
     * @param data         :Data
     * @param currentState :currentState
     * @param error        :Exception
     */
    public RandomJokesViewState(RandomJokesModel data, int currentState, Throwable error) {
        this.data = data;
        this.currentState = currentState;
        this.error = error;
    }
}
