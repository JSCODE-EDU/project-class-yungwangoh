package com.example.yun.exception;

public class GoodOutOfLengthException extends IndexOutOfBoundsException {

    public GoodOutOfLengthException(String s) {
        super(s);
    }
}
