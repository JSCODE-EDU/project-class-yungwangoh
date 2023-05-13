package com.example.yun.controller.exception;

import com.example.yun.error.ErrorResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "예외", description = "유효하지 않은 id 값의 예외")
    @ApiResponse(responseCode = "404", description = "NOT FOUND")
    public ResponseEntity<ErrorResult> notFoundException(IllegalArgumentException exception) {
        log.info("[exception]", exception);

        return getErrorResultResponseEntity(HttpStatus.NOT_FOUND, exception);
    }

    private static ResponseEntity<ErrorResult> getErrorResultResponseEntity(HttpStatus status, Exception exception) {
        ErrorResult errorResult = ErrorResult.of(status, exception.getMessage());
        return new ResponseEntity<>(errorResult, status);
    }
}
