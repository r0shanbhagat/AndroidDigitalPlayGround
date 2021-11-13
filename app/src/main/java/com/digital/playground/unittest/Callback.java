package com.digital.playground.unittest;

public interface Callback<T> {
    void reply(T response);
}