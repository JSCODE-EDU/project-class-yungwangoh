package com.example.yun.service.impl;

import com.example.yun.domain.Board;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.repository.BoardRepository;
import com.example.yun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시물 등록
     * @param title 제목
     * @param content 내용
     * @return 게시물 응답 Dto(id, title, content)
     */
    @Override
    @Transactional
    public BoardResponseDto boardCreate(String title, String content) {
        Board board = new Board(title, content);

        Board save = boardRepository.save(board);

        return new BoardResponseDto(save);
    }

    /**
     * 게시물 전체 조회
     * @return 게시물 응답 Dto(id, title, content) List
     */
    @Override
    public List<BoardResponseDto> boardAllSearch() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream().map(BoardResponseDto::new)
                .collect(toList());
    }

    /**
     * 특정 게시물 조회
     * @param id 게시물 id
     * @return 게시물 응답 Dto(id, title, content)
     */
    @Override
    public BoardResponseDto boardSearch(Long id) {
        Optional<Board> board = getBoard(id);

        return new BoardResponseDto(getBoard(board));
    }

    /**
     * 게시물 제목 수정
     * @param id 게시물 id
     * @param title 제목
     * @return 수정된 게시물 응답 Dto(id, title, content)
     */
    @Override
    @Transactional
    public BoardResponseDto boardUpdateTitle(Long id, String title) {
        Optional<Board> board = getBoard(id);

        getBoard(board).updateTitle(title);

        Optional<Board> updateBoard = getBoard(id);

        return new BoardResponseDto(getBoard(updateBoard));
    }

    /**
     * 게시물 내용 수정
     * @param id 게시물 id
     * @param content 내용
     * @return 수정된 게시물 응답 Dto(id, title, content)
     */
    @Override
    @Transactional
    public BoardResponseDto boardUpdateContent(Long id, String content) {
        Optional<Board> board = getBoard(id);

        getBoard(board).updateContent(content);

        Optional<Board> updateContent = getBoard(id);

        return new BoardResponseDto(getBoard(updateContent));
    }

    /**
     * 게시물 삭제
     * @param id 게시물 id
     */
    @Override
    @Transactional
    public void boardDelete(Long id) {

        Optional<Board> board = getBoard(id);

        boardRepository.delete(getBoard(board));


    }

    /**
     * 게시물 찾기 -> 없으면 예외
     * @param id 게시물 id
     * @return Optional wrapped board
     */
    private Optional<Board> getBoard(Long id) {
        return of(boardRepository.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("게시물을 조회할 수 없습니다."));
    }

    /**
     * Optional wrapped 게시물 해제
     * @param board wrapped 게시물
     * @return 게시물
     */
    private static Board getBoard(Optional<Board> board) {
        return board.get();
    }
}
