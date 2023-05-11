package com.example.yun.repository.querydsl;

import com.example.yun.domain.board.Board;

import java.util.List;

public interface BoardQueryRepository {

    default List<Board> boardAllSearchBySort() {
        return null;
    }

    default List<Board> boardSearchByKeyword(String keyword) {
        return null;
    }
}
