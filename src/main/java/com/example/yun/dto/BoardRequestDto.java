package com.example.yun.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "제목", example = "안녕하세요", required = true)
    private String title;

    @NotBlank
    @Size(max = 1000)
    @ApiModelProperty(value = "내용", example = "ㅎㅇ요", required = true)
    private String content;
}
