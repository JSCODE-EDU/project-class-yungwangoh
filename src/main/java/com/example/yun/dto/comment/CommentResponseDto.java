package com.example.yun.dto.comment;

import com.example.yun.domain.Comment.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {

    private Long id;
    private String content;
    private LocalDateTime createTime;

    private CommentResponseDto(Long id, String content, LocalDateTime createTime) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
    }

    public static CommentResponseDto commentResponseCreate(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getCreateTime());
    }
}
