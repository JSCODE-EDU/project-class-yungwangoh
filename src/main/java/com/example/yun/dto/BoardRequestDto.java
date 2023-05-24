package com.example.yun.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    @NotBlank
    @Size(max = 15)
    @ApiModelProperty(value = "제목", example = "안녕하세요", required = true)
    private String title;

    @NotBlank
    @Size(max = 1000)
    @ApiModelProperty(value = "내용", example = "ㅎㅇ요", required = true)
    private String content;

    @Email
    @ApiModelProperty(value = "이메일", example = "sawew1234@google.com", required = true)
    private String email;

    private BoardRequestDto(final String title, final String content, final String email) {
        this.title = title;
        this.content = content;
        this.email = email;
    }

    public static BoardRequestDto boardRequestCreate(final String title, final String content, final String email) {
        return new BoardRequestDto(title, content, email);
    }
}
