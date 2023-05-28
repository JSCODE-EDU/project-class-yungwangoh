package com.example.yun.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ExceptionControl {

    NOT_FOUND_MEMBER("회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD("게시물을 찾을 수 없습니다."),
    GOOD_OUT_OF_LENGTH("좋아요 수가 범위를 초과했습니다."),
    GOOD_INVALIDATED("좋아요 기능을 수행할 수 없습니다."),
    LOG_IN_FAILED("로그인을 할 수 없습니다.");

    private final String message;

    ExceptionControl(String message) {
        this.message = message;
    }

    public NotFoundException notFoundCreate() {
        return new NotFoundException(this.message);
    }
    public GoodException goodCreate() {
        return new GoodException(this.message);
    }
}
