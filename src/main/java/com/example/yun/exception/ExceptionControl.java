package com.example.yun.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public enum ExceptionControl {

    NOT_FOUND_MEMBER(NOT_FOUND,"회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD(NOT_FOUND,"게시물을 찾을 수 없습니다."),
    GOOD_OUT_OF_LENGTH(BAD_REQUEST, "좋아요 수가 범위를 초과했습니다."),
    GOOD_INVALIDATED(BAD_REQUEST, "좋아요 기능을 수행할 수 없습니다.");

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
