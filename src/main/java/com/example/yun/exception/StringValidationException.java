package com.example.yun.exception;

public class StringValidationException extends IllegalArgumentException {

    public StringValidationException() {
    }

    public StringValidationException(String s) {
        super(s);
    }

    public StringValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StringValidationException(Throwable cause) {
        super(cause);
    }
}
