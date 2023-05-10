package com.example.yun.controller;

import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api")
    ResponseEntity<BoardResponseDto> boardCreateApi(@RequestBody BoardRequestDto boardRequestDto) {

        log.info("board request dto = {} ", boardRequestDto);

        BoardResponseDto boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                boardRequestDto.getContent());

        return new ResponseEntity<>(boardResponseDto, CREATED);
    }

    @GetMapping("/api/{boardId}")
    ResponseEntity<BoardResponseDto> boardSearchApi(@PathVariable Long boardId) {

        log.info("board id = {}", boardId);

        BoardResponseDto boardResponseDto = boardService.boardSearch(boardId);

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @GetMapping("/api/keyword")
    ResponseEntity<List<BoardResponseDto>> boardAllSearchByKeywordApi(@RequestParam("keyword") String keyword) {

        List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearchByKeyword(keyword);

        return new ResponseEntity<>(boardResponseDtos, OK);
    }

    @GetMapping("/api")
    ResponseEntity<List<BoardResponseDto>> boardAllSearchApi() {

        List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearch();

        return new ResponseEntity<>(boardResponseDtos, OK);
    }

    @PatchMapping("/api/title")
    ResponseEntity<BoardResponseDto> boardTitleUpdateApi(@RequestBody BoardTitleUpdateDto boardTitleUpdateDto) {

        log.info("board title update dto = {}", boardTitleUpdateDto);

        BoardResponseDto boardResponseDto = boardService.boardUpdateTitle(boardTitleUpdateDto.getId(),
                boardTitleUpdateDto.getTitle());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @PatchMapping("/api/content")
    ResponseEntity<BoardResponseDto> boardContentUpdateApi(@RequestBody BoardContentUpdateDto boardContentUpdateDto) {

        log.info("board content update dto = {}", boardContentUpdateDto);

        BoardResponseDto boardResponseDto = boardService.boardUpdateContent(boardContentUpdateDto.getId(),
                boardContentUpdateDto.getContent());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @DeleteMapping("/api/{boardId}")
    ResponseEntity<String> boardDeleteApi(@PathVariable Long boardId) {

        log.info("board id = {}", boardId);

        String boardDelete = boardService.boardDelete(boardId);

        return new ResponseEntity<>(boardDelete, NO_CONTENT);
    }
}
