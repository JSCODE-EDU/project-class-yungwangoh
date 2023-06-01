package com.example.yun.service;

import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.page.PageResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    default BoardResponseDto boardCreate(String title, String content, Long memberId) {
        return null;
    }

    default List<BoardResponseDto> boardAllSearch() {
        return null;
    }

    default BoardResponseDto boardSearch(Long id) {
        return null;
    }

    default BoardResponseDto boardUpdateTitle(Long id, String title) {
        return null;
    }

    default BoardResponseDto boardUpdateContent(Long id, String content) {
        return null;
    }

    default String boardDelete(Long id) { return null; }

    default List<BoardResponseDto> boardAllSearchBySort() { return null; }

    default List<BoardResponseDto> boardAllSearchByKeyword(String keyword) {
        return null;
    }

    default PageResponseDto boardPagination(Pageable pageable) {
        return null;
    }
}
