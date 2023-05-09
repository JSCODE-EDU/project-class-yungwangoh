package com.example.yun.controller.exception;

import com.example.yun.error.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("com.example.yun.controller")
public class BoardException {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResult> notFoundException(IllegalArgumentException exception) {
        log.info("[exception]", exception);

        return getErrorResultResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    private static ResponseEntity<ErrorResult> getErrorResultResponseEntity(HttpStatus status, Exception exception) {
        ErrorResult errorResult = new ErrorResult(status, exception.getMessage());
        return new ResponseEntity<>(errorResult, status);
    }
}
