package com.example.yun.exception;

public class GoodOutOfLengthException extends IndexOutOfBoundsException {
    public GoodOutOfLengthException() {
        super();
    }

    public GoodOutOfLengthException(String s) {
        super(s);
    }

    public GoodOutOfLengthException(int index) {
        super(index);
    }

    public GoodOutOfLengthException(long index) {
        super(index);
    }
}
