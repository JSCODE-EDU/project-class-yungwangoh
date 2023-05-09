package com.example.yun.controller;

import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api")
    ResponseEntity<BoardResponseDto> boardCreateApi(@RequestBody BoardRequestDto boardRequestDto) {

        BoardResponseDto boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                boardRequestDto.getContent());

        return new ResponseEntity<>(boardResponseDto, CREATED);
    }

    @GetMapping("/api/{boardId}")
    ResponseEntity<BoardResponseDto> boardSearchApi(@PathVariable Long boardId) {

        BoardResponseDto boardResponseDto = boardService.boardSearch(boardId);

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @GetMapping("/api")
    ResponseEntity<List<BoardResponseDto>> boardAllSearchApi() {

        List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearch();

        return new ResponseEntity<>(boardResponseDtos, OK);
    }

    @PatchMapping("/api/title")
    ResponseEntity<BoardResponseDto> boardTitleUpdateApi(@RequestBody BoardTitleUpdateDto boardTitleUpdateDto) {

        BoardResponseDto boardResponseDto = boardService.boardUpdateTitle(boardTitleUpdateDto.getId(),
                boardTitleUpdateDto.getTitle());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @PatchMapping("/api/content")
    ResponseEntity<BoardResponseDto> boardContentUpdateApi(@RequestBody BoardContentUpdateDto boardContentUpdateDto) {

        BoardResponseDto boardResponseDto = boardService.boardUpdateContent(boardContentUpdateDto.getId(),
                boardContentUpdateDto.getContent());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @DeleteMapping("/api/{boardId}")
    ResponseEntity<Void> boardDeleteApi(@PathVariable Long boardId) {

        boardService.boardDelete(boardId);

        return new ResponseEntity<>(NO_CONTENT);
    }
}
