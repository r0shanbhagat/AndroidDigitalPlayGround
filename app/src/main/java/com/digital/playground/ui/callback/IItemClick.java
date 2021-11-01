package com.digital.playground.ui.callback;

public interface IItemClick<T> {
    default void onItemClick(T item) {
    }

    default void retryPageLoad() {
    }

}
