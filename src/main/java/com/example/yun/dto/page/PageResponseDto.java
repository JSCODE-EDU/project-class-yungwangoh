package com.example.yun.dto.page;

import com.example.yun.domain.board.Board;
import com.example.yun.dto.BoardResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageResponseDto {

    private List<BoardResponseDto> boardResponseDtos;
    private int pageCount;

    private PageResponseDto(final List<BoardResponseDto> boardResponseDtos, final int pageCount) {
        this.boardResponseDtos = boardResponseDtos;
        this.pageCount = pageCount;
    }

    public static PageResponseDto pageCreate(Page<Board> boards) {

        List<BoardResponseDto> boardResponseDtoList = boards.getContent().stream()
                .map(BoardResponseDto::from)
                .collect(toList());

        return new PageResponseDto(boardResponseDtoList, boards.getTotalPages());
    }
}
