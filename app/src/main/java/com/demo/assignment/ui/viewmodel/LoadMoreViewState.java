package com.demo.assignment.ui.viewmodel;


import com.demo.assignment.core.BaseViewState;
import com.demo.assignment.repository.model.HerosModel;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreViewState extends BaseViewState<List<HerosModel>> {
    public static final LoadMoreViewState ERROR_STATE = new LoadMoreViewState(null, State.FAILED.value, new Throwable());
    public static final LoadMoreViewState LOADING_STATE = new LoadMoreViewState(null, State.LOADING.value, null);
    public static final LoadMoreViewState SUCCESS_STATE = new LoadMoreViewState(new ArrayList<>(), State.SUCCESS.value, null);

    /**
     * @param data         :Data
     * @param currentState :currentState
     * @param error        :Exception
     */
    public LoadMoreViewState(List<HerosModel> data, int currentState, Throwable error) {
        this.data = data;
        this.currentState = currentState;
        this.error = error;
    }
}
