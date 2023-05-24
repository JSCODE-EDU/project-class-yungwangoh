package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.member.QMember;
import com.example.yun.repository.querydsl.CommentQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.yun.domain.Comment.QComment.*;
import static com.example.yun.domain.board.QBoard.*;
import static com.example.yun.domain.member.QMember.*;

@Repository
@RequiredArgsConstructor
public class CommentQueryDslRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> findCommentsByBoardId(Long boardId) {
        return jpaQueryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardId))
                .join(comment.board, board).fetchJoin()
                .join(comment.member, member).fetchJoin()
                .fetch();
    }
}
