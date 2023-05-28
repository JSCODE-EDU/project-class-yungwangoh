package com.example.yun.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    private String content;

    private CommentRequestDto(String content) {
        this.content = content;
    }

    public static CommentRequestDto commentRequestCreate(String content) {
        return new CommentRequestDto(content);
    }
}
