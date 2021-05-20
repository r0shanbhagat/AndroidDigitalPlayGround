package com.demo.assignment.core;

/**
 * Abstract BaseViewState class to maintain the state of service call from ViewModel
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
        LOADING(0), SUCCESS(1), FAILED(-1);
        public final int value;

        State(int val) {
            value = val;
        }
    }
}

