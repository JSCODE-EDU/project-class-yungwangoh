package com.example.yun.dto.comment;

import com.example.yun.domain.Comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {

    private String content;
    private LocalDateTime createTime;

    private CommentResponseDto(String content, LocalDateTime createTime) {
        this.content = content;
        this.createTime = createTime;
    }

    public static CommentResponseDto commentResponseCreate(Comment comment) {
        return new CommentResponseDto(comment.getContent(), comment.getCreateTime());
    }
}
