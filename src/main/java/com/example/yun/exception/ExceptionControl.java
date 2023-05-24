package com.example.yun.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ExceptionControl {

    NOT_FOUND_MEMBER(NOT_FOUND,"회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD(NOT_FOUND,"게시물을 찾을 수 없습니다.");

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionControl(HttpStatus httpStatus, String message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
