package com.example.yun.controller.exception;

import com.example.yun.error.ErrorResult;
import com.example.yun.exception.StringValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(summary = "예외", description = "제목과 내용이 주어진 형식에 맞지 않는 경우")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    public ResponseEntity<ErrorResult> titleOrContentValidatedException(StringValidationException exception) {
        log.info("[exception]", exception);

        return getErrorResultResponseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Operation(summary = "예외", description = "Request Param 유효성")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    public ResponseEntity<ErrorResult> badRequestException(ConstraintViolationException exception) {
        log.info("[exception]", exception);

        return getErrorResultResponseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Operation(summary = "예외", description = "RuntimeException 처리")
    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR")
    public ResponseEntity<ErrorResult> runTimeException(RuntimeException exception) {
        log.info("[exception]", exception);

        return getErrorResultResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private static ResponseEntity<ErrorResult> getErrorResultResponseEntity(HttpStatus status, Exception exception) {
        ErrorResult errorResult = ErrorResult.of(status, exception.getMessage());
        return new ResponseEntity<>(errorResult, status);
    }
}
