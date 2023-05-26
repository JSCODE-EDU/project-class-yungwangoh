package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Good;
import com.example.yun.domain.member.Member;
import com.example.yun.exception.ExceptionControl;
import com.example.yun.exception.GoodInvalidatedException;
import com.example.yun.exception.NotFoundBoardException;
import com.example.yun.exception.NotFoundMemberException;
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
    public void goodUp(String jwt, Long boardId) {
        Long memberId = jwtProvider.mapTokenToId(jwt);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException(NOT_FOUND_MEMBER.getMessage()));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundBoardException(NOT_FOUND_BOARD.getMessage()));

        board.goodUp();

        log.info("service board = {} ", board);

        Good good = Good.goodCreate(member, board);

        likeRepository.save(good);
    }

    @Override
    @Transactional
    public void goodDown(String jwt, Long boardId) {
        Long memberId = jwtProvider.mapTokenToId(jwt);

        boolean check = likeQueryRepository.findGoodMemberIdAndBoardId(memberId, boardId);
        log.info("check = {} ",check);

        if(check) {
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new NotFoundBoardException(NOT_FOUND_BOARD.getMessage()));

            board.goodDown();
        } else {
            throw new GoodInvalidatedException(GOOD_INVALIDATED.getMessage());
        }
    }
}
