package com.example.yun.dto;

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
    private String title;
    @NotBlank
    @Size(max = 1000)
    private String content;
}
