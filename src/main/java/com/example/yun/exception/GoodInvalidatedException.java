package com.example.yun.exception;

public class GoodInvalidatedException extends IllegalArgumentException{
    public GoodInvalidatedException() {
    }

    public GoodInvalidatedException(String s) {
        super(s);
    }

    public GoodInvalidatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoodInvalidatedException(Throwable cause) {
        super(cause);
    }
}
