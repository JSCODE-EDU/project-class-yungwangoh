package com.example.yun.repository.querydsl;

import com.example.yun.domain.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryRepository {

    default List<Board> boardAllSearchBySort() {
        return null;
    }

    default List<Board> boardSearchByKeyword(String keyword) {
        return null;
    }
    default Page<Board> boardPageNation(Pageable pageable) {
        return null;
    };

    default void likeUp(Long boardId) {}

    default void likeDown(Long boardId) {}
}
