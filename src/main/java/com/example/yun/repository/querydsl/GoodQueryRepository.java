package com.example.yun.repository.querydsl;

public interface GoodQueryRepository {

    default boolean findGoodMemberIdAndBoardId(Long memberId, Long boardId) {
        return false;
    }
}
