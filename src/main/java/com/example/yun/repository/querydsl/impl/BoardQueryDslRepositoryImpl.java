package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.repository.querydsl.BoardQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.yun.domain.board.QBoard.board;

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
                .where(board.title.contains(keyword))
                .orderBy(board.createTime.desc())
                .limit(100L)
                .fetch();
    }
}
