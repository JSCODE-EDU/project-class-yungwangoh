package com.example.yun.controller;

import com.example.yun.custom.annotation.JwtMemberId;
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
@RequestMapping("/api/boards")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comment")
    public ResponseEntity<CommentResponseDto> createCommentApi(@JwtMemberId Long memberId,
                                                               @PathVariable Long boardId,
                                                               @RequestBody @Valid CommentRequestDto commentRequestDto) {

        CommentResponseDto commentCreate = commentService.commentCreate(boardId, memberId, commentRequestDto.getContent());

        return new ResponseEntity<>(commentCreate, CREATED);
    }

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentApi(@PathVariable Long boardId) {
        List<CommentResponseDto> commentResponseDtos = commentService.findCommentsBoardById(boardId);

        return new ResponseEntity<>(commentResponseDtos, OK);
    }
}


