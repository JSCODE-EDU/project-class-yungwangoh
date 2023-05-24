package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.board.QGood;
import com.example.yun.repository.querydsl.GoodQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.yun.domain.board.QGood.*;


@Repository
@RequiredArgsConstructor
public class GoodQueryDslRepository implements GoodQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean findGoodMemberIdAndBoardId(Long memberId, Long boardId) {
        return jpaQueryFactory.selectFrom(good)
                .where(good.board.id.eq(boardId).and(good.member.id.eq(memberId)))
                .join(good.member).fetchJoin()
                .join(good.board).fetchJoin()
                .fetchOne() != null;
    }
}
