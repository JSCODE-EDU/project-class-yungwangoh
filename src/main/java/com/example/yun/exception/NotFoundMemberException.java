package com.example.yun.exception;

public class NotFoundMemberException extends IllegalArgumentException {

    public NotFoundMemberException() {
    }

    public NotFoundMemberException(String s) {
        super(s);
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMemberException(Throwable cause) {
        super(cause);
    }
}
