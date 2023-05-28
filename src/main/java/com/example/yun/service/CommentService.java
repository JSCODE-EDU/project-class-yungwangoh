package com.example.yun.service;

import com.example.yun.dto.comment.CommentResponseDto;

import java.util.List;

public interface CommentService {

    default CommentResponseDto commentCreate(Long boardId, Long memberId, String content) {
        return null;
    }

    default List<CommentResponseDto> findCommentsBoardById(Long boardId) {
        return null;
    }

    default CommentResponseDto updateComment(Long boardId, String updateContent) {
        return null;
    }

    default String deleteComment(Long comment) {
        return null;
    }

}
