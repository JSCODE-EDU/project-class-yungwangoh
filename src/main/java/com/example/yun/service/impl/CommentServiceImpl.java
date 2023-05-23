package com.example.yun.service.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.comment.CommentResponseDto;
import com.example.yun.repository.BoardRepository;
import com.example.yun.repository.comment.CommentRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.CommentQueryRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final BoardRepository boardRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    @Transactional
    public CommentResponseDto commentCreate(Long boardId, String email, String content) {

        Member member = memberQueryRepository.findMemberByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Comment comment = Comment.commentCreate(content, member, board);

        Comment save = commentRepository.save(comment);

        return CommentResponseDto.commentResponseCreate(save);
    }

    @Override
    public List<CommentResponseDto> findCommentsBoardById(Long boardId) {
        List<Comment> comments = commentQueryRepository.findCommentsByBoardId(boardId);

        return comments.stream().map(CommentResponseDto::commentResponseCreate)
                .collect(toList());
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long boardId, String updateContent) {
        return CommentService.super.updateComment(boardId, updateContent);
    }
}
