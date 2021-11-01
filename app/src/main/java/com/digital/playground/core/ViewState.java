package com.digital.playground.core;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract BaseViewState :ViewState—State Reducer
 * *STATE_LOADING for the initial, loading state
 * *STATE_SUCCESS for the Success state
 * *STATE_FAILURE for the Failure state
 * *STATE_EMPTY for Empty Response from Server
 * *STATE_LOAD_MORE for Paging to show a loader to fetch More
 */
public abstract class ViewState {
    public static final int LOADING = 0;
    protected Throwable error;
    public static final int SUCCESS = 1;
    public static final int FAILED = -1;
    public static final int EMPTY = 2;
    protected int currentState;
    protected Object data;

    public ViewState(@State int currentState) {
        this.currentState = currentState;
    }


    public ViewState(@State int currentState, Object data) {
        this.currentState = currentState;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(@State int currentState) {
        this.currentState = currentState;
    }

    @IntDef({LOADING, SUCCESS, FAILED, EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }


}

