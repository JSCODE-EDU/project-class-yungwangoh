package com.example.yun.controller;

import com.example.yun.dto.comment.CommentRequestDto;
import com.example.yun.dto.comment.CommentResponseDto;
import com.example.yun.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> createCommentApi(@RequestHeader("Authorizaiton") String jwt,
                                                               @RequestBody @Valid CommentRequestDto commentRequestDto)
                                                                throws JsonProcessingException {

        CommentResponseDto commentCreate = commentService.commentCreate(commentRequestDto.getBoardId(), jwt, commentRequestDto.getContent());

        return new ResponseEntity<>(commentCreate, CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> findCommentApi(@PathVariable Long boardId) {
        List<CommentResponseDto> commentResponseDtos = commentService.findCommentsBoardById(boardId);

        return new ResponseEntity<>(commentResponseDtos, OK);
    }
}


