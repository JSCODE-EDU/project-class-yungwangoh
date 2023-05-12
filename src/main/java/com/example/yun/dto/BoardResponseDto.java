package com.example.yun.dto;

import com.example.yun.domain.board.Board;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private static final List<BoardResponseDto> list = new ArrayList<>();
    private Long id;
    private String title;
    private String content;

    private BoardResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent());
    }

    public static List<BoardResponseDto> responseDtosCreate(List<Board> boards) {
        return boards.stream().map(BoardResponseDto::from)
                .collect(toList());
    }
}
