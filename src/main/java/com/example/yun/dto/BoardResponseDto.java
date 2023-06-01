package com.example.yun.dto;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Content;
import com.example.yun.domain.board.Title;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.yun.domain.board.Content.*;
import static com.example.yun.domain.board.Title.*;

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
