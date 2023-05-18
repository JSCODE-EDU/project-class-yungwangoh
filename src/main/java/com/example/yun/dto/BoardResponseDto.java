package com.example.yun.dto;

import com.example.yun.domain.board.Board;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    @ApiModelProperty(value = "게시물 id", required = true)
    private Long id;
    @ApiModelProperty(value = "제목", example = "안녕하세요", required = true)
    private String title;
    @ApiModelProperty(value = "내용", example = "ㅎㅇ요", required = true)
    private String content;

    private BoardResponseDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static BoardResponseDto from(Board board) {
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent());
    }
}
