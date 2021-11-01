package com.digital.playground.ui.viewmodel;


import com.digital.playground.core.BaseViewState;
import com.digital.playground.repository.model.TopRatedMovies;

public class LoadMoreViewState extends BaseViewState<TopRatedMovies> {
    public static final LoadMoreViewState ERROR_STATE = new LoadMoreViewState(null, State.FAILED.value, new Throwable());
    public static final LoadMoreViewState LOADING_STATE = new LoadMoreViewState(null, State.LOADING.value, null);
    public static final LoadMoreViewState SUCCESS_STATE = new LoadMoreViewState(new TopRatedMovies(), State.SUCCESS.value, null);
    public static final LoadMoreViewState EMPTY_STATE = new LoadMoreViewState(new TopRatedMovies(), State.EMPTY.value, null);

    /**
     * @param data         :Data
     * @param currentState :currentState
     * @param error        :Exception
     */
    public LoadMoreViewState(TopRatedMovies data, int currentState, Throwable error) {
        this.data = data;
        this.currentState = currentState;
        this.error = error;
    }
}
