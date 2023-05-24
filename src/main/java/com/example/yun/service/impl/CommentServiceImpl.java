package com.example.yun.service.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.comment.CommentResponseDto;
import com.example.yun.exception.NotFoundBoardException;
import com.example.yun.exception.NotFoundMemberException;
import com.example.yun.jwt.JwtObject;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.BoardRepository;
import com.example.yun.repository.comment.CommentRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.CommentQueryRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.yun.exception.ExceptionControl.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public CommentResponseDto commentCreate(Long boardId, String jwt, String content) {

        Long userId = jwtProvider.mapTokenToId(jwt);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER.getMessage()));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException(NOT_FOUND_BOARD.getMessage()));

        Comment comment = Comment.commentCreate(content, member, board);

        Comment save = commentRepository.save(comment);

        return CommentResponseDto.commentResponseCreate(save);
    }

    @Override
    public List<CommentResponseDto> findCommentsBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException(NOT_FOUND_BOARD.getMessage()));

        List<Comment> comments = commentQueryRepository.findCommentsByBoardId(board.getId());

        return comments.stream().map(CommentResponseDto::commentResponseCreate)
                .collect(toList());
    }

}
