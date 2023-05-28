package com.example.yun.service.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.comment.CommentResponseDto;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.comment.CommentRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.CommentQueryRepository;
import com.example.yun.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.yun.exception.ExceptionControl.NOT_FOUND_BOARD;
import static com.example.yun.exception.ExceptionControl.NOT_FOUND_MEMBER;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public CommentResponseDto commentCreate(Long boardId, Long memberId, String content) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NOT_FOUND_MEMBER::notFoundCreate);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NOT_FOUND_BOARD::notFoundCreate);

        Comment comment = Comment.commentCreate(content, member, board);

        Comment save = commentRepository.save(comment);

        return CommentResponseDto.commentResponseCreate(save);
    }

    @Override
    public List<CommentResponseDto> findCommentsBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NOT_FOUND_BOARD::notFoundCreate);

        List<Comment> comments = commentQueryRepository.findCommentsByBoardId(board.getId());

        return comments.stream().map(CommentResponseDto::commentResponseCreate)
                .collect(toList());
    }

}
