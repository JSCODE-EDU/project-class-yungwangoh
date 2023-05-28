package com.example.yun.exception;

public class LoginException extends IllegalStateException {

    public LoginException() {
    }

    public LoginException(String s) {
        super(s);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
