package com.example.yun.dto.comment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

    private String content;
    private String email;
    private Long boardId;

    private CommentRequestDto(String content, String email, Long boardId) {
        this.content = content;
        this.email = email;
        this.boardId = boardId;
    }

    public static CommentRequestDto commentRequestCreate(String content, String email, Long boardId) {
        return new CommentRequestDto(content, email, boardId);
    }
}
