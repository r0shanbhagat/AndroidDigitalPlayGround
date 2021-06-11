package com.demo.assignment.ui.callback;

public interface IItemClick<T> {
    default void onItemClick(T item) {
    }

    default void retryPageLoad() {
    }

}
