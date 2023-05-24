package com.example.yun.exception;

public class NotFoundBoardException extends IllegalArgumentException {

    public NotFoundBoardException() {
    }

    public NotFoundBoardException(String s) {
        super(s);
    }

    public NotFoundBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundBoardException(Throwable cause) {
        super(cause);
    }
}
