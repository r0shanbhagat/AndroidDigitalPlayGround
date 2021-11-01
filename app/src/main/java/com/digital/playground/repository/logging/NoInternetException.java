package com.digital.playground.repository.logging;

import java.io.IOException;

public class NoInternetException extends IOException {
    public NoInternetException(String msg, Throwable cause) {
        super(msg, cause);
    }
}