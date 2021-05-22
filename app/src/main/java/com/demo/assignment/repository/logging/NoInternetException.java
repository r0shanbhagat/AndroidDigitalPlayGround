package com.demo.assignment.repository.logging;

import java.io.IOException;

public class NoInternetException extends IOException {
    public NoInternetException(String msg, Throwable cause) {
        super(msg, cause);
    }
}