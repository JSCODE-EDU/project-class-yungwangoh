package com.example.yun.repository.querydsl;

import com.example.yun.domain.Comment.Comment;

import java.util.List;

public interface CommentQueryRepository {

    default List<Comment> findCommentsByBoardId(Long boardId) {
        return null;
    }
}
