package com.example.yun.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResult {

    @ApiModelProperty(value = "Http 상태 코드", required = true)
    private HttpStatus httpStatus;

    @ApiModelProperty(value = "예외 메세지", required = true)
    private String message;

    public static ErrorResult of(HttpStatus httpStatus, String message) {
        return new ErrorResult(httpStatus, message);
    }
}
