package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Good;
import com.example.yun.domain.member.Member;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.board.GoodRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.GoodQueryRepository;
import com.example.yun.service.GoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.yun.exception.ExceptionControl.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class GoodServiceImpl implements GoodService {

    private final GoodRepository likeRepository;
    private final GoodQueryRepository likeQueryRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void goodUp(Long memberId, Long boardId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(NOT_FOUND_MEMBER::notFoundCreate);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(NOT_FOUND_BOARD::notFoundCreate);

        board.goodUp();

        log.info("service board = {} ", board);

        Good good = Good.goodCreate(member, board);

        likeRepository.save(good);
    }

    @Override
    @Transactional
    public void goodDown(Long memberId, Long boardId) {

        boolean check = likeQueryRepository.findGoodMemberIdAndBoardId(memberId, boardId);
        log.info("check = {} ",check);

        if(check) {
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(NOT_FOUND_BOARD::notFoundCreate);

            board.goodDown();
        } else {
            throw GOOD_INVALIDATED.goodCreate();
        }
    }
}
