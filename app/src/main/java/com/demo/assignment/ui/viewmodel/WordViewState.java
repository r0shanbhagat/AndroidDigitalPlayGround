package com.demo.assignment.ui.viewmodel;


import com.demo.assignment.core.BaseViewState;
import com.demo.assignment.database.Word;

import java.util.ArrayList;
import java.util.List;

public class WordViewState extends BaseViewState<List<Word>> {
    public static final WordViewState ERROR_STATE = new WordViewState(null, State.FAILED.value, new Throwable());
    public static final WordViewState LOADING_STATE = new WordViewState(null, State.LOADING.value, null);
    public static final WordViewState SUCCESS_STATE = new WordViewState(new ArrayList<>(), State.SUCCESS.value, null);

    /**
     * @param data         :Data
     * @param currentState :currentState
     * @param error        :Exception
     */
    public WordViewState(List<Word> data, int currentState, Throwable error) {
        this.data = data;
        this.currentState = currentState;
        this.error = error;
    }
}
