package com.example.yun.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    private String content;
    private Long boardId;

    private CommentRequestDto(String content, Long boardId) {
        this.content = content;
        this.boardId = boardId;
    }

    public static CommentRequestDto commentRequestCreate(String content, Long boardId) {
        return new CommentRequestDto(content, boardId);
    }
}
