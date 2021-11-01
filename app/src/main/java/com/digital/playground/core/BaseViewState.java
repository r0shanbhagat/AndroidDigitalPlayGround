package com.digital.playground.core;

/**
 * Abstract BaseViewState :ViewState—State Reducer
 * *STATE_LOADING for the initial, loading state
 * *STATE_SUCCESS for the Success state
 * *STATE_FAILURE for the Failure state
 * *STATE_EMPTY for Empty Response from Server
 * *STATE_LOAD_MORE for Paging to show a loader to fetch More
 *
 * @param <T>
 */
public abstract class BaseViewState<T> {
    protected T data;
    protected Throwable error;
    protected int currentState;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public enum State {
        LOADING(0), SUCCESS(1), FAILED(-1), EMPTY(2);
        public final int value;

        State(int val) {
            value = val;
        }
    }
}

