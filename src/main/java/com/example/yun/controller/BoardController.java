package com.example.yun.controller;

import com.example.yun.custom.annotation.JwtMemberId;
import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.page.PageResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.error.ErrorResult;
import com.example.yun.service.BoardService;
import com.example.yun.service.GoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/boards")
@Validated
public class BoardController {

    private final BoardService boardService;
    private final GoodService goodService;

    @PostMapping("")
    @Operation(summary = "게시물 등록", description = "게시물을 등록 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = BoardResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ErrorResult.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(implementation = ErrorResult.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<BoardResponseDto> boardCreateApi(@RequestBody @Valid final BoardRequestDto boardRequestDto,
                                                    @JwtMemberId final Long memberId) {

        log.info("board request dto = {} ", boardRequestDto);
        log.info("memberId = {}", memberId);

        BoardResponseDto boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                boardRequestDto.getContent(), memberId);

        return new ResponseEntity<>(boardResponseDto, CREATED);
    }

    @GetMapping("/{boardId}")
    @Operation(summary = "게시물 찾기", description = "게시물 id를 통해 특정 게시물을 찾는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<BoardResponseDto> boardSearchApi(@PathVariable @NotNull final Long boardId) {

        log.info("board id = {}", boardId);

        BoardResponseDto boardResponseDto = boardService.boardSearch(boardId);

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @GetMapping("/keyword")
    @Operation(summary = "게시물 찾기", description = "키워드를 통해 특정 게시물을 찾는다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<List<BoardResponseDto>> boardAllSearchByKeywordApi(@RequestParam("keyword") @NotBlank String keyword) {

        List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearchByKeyword(keyword);

        return new ResponseEntity<>(boardResponseDtos, OK);
    }

    @GetMapping("")
    @Operation(summary = "전체 게시물 조회", description = "전체 게시물을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<List<BoardResponseDto>> boardAllSearchApi() {

        List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearchBySort();

        return new ResponseEntity<>(boardResponseDtos, OK);
    }

    @PatchMapping("/title")
    @Operation(summary = "제목 수정", description = "제목을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<BoardResponseDto> boardTitleUpdateApi(@RequestBody @Valid final BoardTitleUpdateDto boardTitleUpdateDto) {

        log.info("board title update dto = {}", boardTitleUpdateDto);

        BoardResponseDto boardResponseDto = boardService.boardUpdateTitle(boardTitleUpdateDto.getId(),
                boardTitleUpdateDto.getTitle());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @PatchMapping("/content")
    @Operation(summary = "내용 수정", description = "내용을 수정 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<BoardResponseDto> boardContentUpdateApi(@RequestBody @Valid final BoardContentUpdateDto boardContentUpdateDto) {

        log.info("board content update dto = {}", boardContentUpdateDto);

        BoardResponseDto boardResponseDto = boardService.boardUpdateContent(boardContentUpdateDto.getId(),
                boardContentUpdateDto.getContent());

        return new ResponseEntity<>(boardResponseDto, OK);
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시물 삭제", description = "게시물을 삭제 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<String> boardDeleteApi(@PathVariable @NotNull final Long boardId) {

        log.info("board id = {}", boardId);

        String boardDelete = boardService.boardDelete(boardId);

        return new ResponseEntity<>(boardDelete, NO_CONTENT);
    }

    @GetMapping("/{boardId}/up")
    @Operation(summary = "좋아요", description = "게시물에 좋아요를 할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<Void> goodUpApi(@JwtMemberId final Long memberId,
                                   @PathVariable final Long boardId) {

        goodService.goodUp(memberId, boardId);

        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{boardId}/down")
    @Operation(summary = "좋아요", description = "게시물에 좋아요를 취소할 수 있다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<Void> goodDownApi(@JwtMemberId final Long memberId,
                                     @PathVariable final Long boardId) {

        goodService.goodDown(memberId, boardId);

        return new ResponseEntity<>(OK);
    }

    @GetMapping("/page/{pageNum}")
    @Operation(summary = "페이지 네이션", description = "페이지 네이션")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "NO CONTENT"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    ResponseEntity<PageResponseDto> boardPaginationApi(@PathVariable int pageNum) {

        PageRequest pageRequest = PageRequest.of(pageNum, 10);

        log.info("[pageRequest] = {}", pageRequest);

        PageResponseDto pageResponseDto = boardService.boardPagination(pageRequest);

        return new ResponseEntity<>(pageResponseDto, OK);
    }
}
