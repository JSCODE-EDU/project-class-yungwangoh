package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.QBoard;
import com.example.yun.domain.member.QMember;
import com.example.yun.repository.querydsl.BoardQueryRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.yun.domain.board.QBoard.board;
import static com.example.yun.domain.member.QMember.*;
import static java.util.Optional.*;

@Repository
@RequiredArgsConstructor
public class BoardQueryDslRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Board> boardAllSearchBySort() {
        return jpaQueryFactory.selectFrom(board)
                .orderBy(board.createTime.desc())
                .limit(100L)
                .fetch();
    }

    @Override
    public List<Board> boardSearchByKeyword(String keyword) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.title.title.contains(keyword))
                .orderBy(board.createTime.desc())
                .limit(100L)
                .fetch();
    }

    @Override
    public Page<Board> boardPageNation(Pageable pageable) {
        List<Board> boards = jpaQueryFactory.selectFrom(board)
                .join(board.member, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> query = jpaQueryFactory.select(board.count())
                .from(board);

        return PageableExecutionUtils.getPage(boards, pageable, query::fetchOne);
    }
}
