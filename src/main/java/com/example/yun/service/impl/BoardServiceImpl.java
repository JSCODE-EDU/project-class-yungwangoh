package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.exception.BoardMessage;
import com.example.yun.repository.BoardRepository;
import com.example.yun.repository.querydsl.BoardQueryRepository;
import com.example.yun.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.yun.domain.board.Content.contentCreate;
import static com.example.yun.domain.board.Title.titleCreate;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    /**
     * 게시물 등록
     * @param title 제목
     * @param content 내용
     * @return 게시물 응답 Dto(id, title, content)
     */
    @Override
    @Transactional
    public BoardResponseDto boardCreate(String title, String content) {
        Board board = new Board(titleCreate(title), contentCreate(content));

        Board save = boardRepository.save(board);

        log.info("save = {}", save);

        return BoardResponseDto.from(save);
    }

    /**
     * 게시물 전체 조회
     * @return 게시물 응답 Dto(id, title, content) List
     */
    @Override
    public List<BoardResponseDto> boardAllSearch() {
        List<Board> boards = boardRepository.findAll();

        log.info("boards = {}", boards);

        return responseDtosCreate(boards);
    }

    /**
     * 특정 게시물 조회
     * @param id 게시물 id
     * @return 게시물 응답 Dto(id, title, content)
     */
    @Override
    public BoardResponseDto boardSearch(Long id) {
        Optional<Board> board = getBoard(id);

        log.info("board = {}", board.get());

        return BoardResponseDto.from(getBoard(board));
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

        log.info("board = {}", board.get());

        getBoard(board).updateTitle(titleCreate(title));

        Optional<Board> updateBoard = getBoard(id);

        log.info("update title board = {}", updateBoard.get());

        return BoardResponseDto.from(getBoard(updateBoard));
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

        log.info("board = {}", board.get());

        getBoard(board).updateContent(contentCreate(content));

        Optional<Board> updateContent = getBoard(id);

        log.info("update content board = {}", updateContent.get());

        return BoardResponseDto.from(getBoard(updateContent));
    }

    /**
     * 게시물 삭제
     * @param id 게시물 id
     */
    @Override
    @Transactional
    public String boardDelete(Long id) {

        Optional<Board> board = getBoard(id);

        log.info("board = {}", board.get());

        boardRepository.delete(getBoard(board));

        return BoardMessage.BOARD_DELETE_SUCCESS;
    }

    /**
     * 시간순 내림차순 정렬하여 모든 게시물 출력 (최대 100개)
     * @return 게시물 응답 리스트 (id, title, content)
     */
    @Override
    public List<BoardResponseDto> boardAllSearchBySort() {
        List<Board> boards = boardQueryRepository.boardAllSearchBySort();

        return responseDtosCreate(boards);
    }

    /**
     * 키워드로 검색 시간순으로 내림차순 정렬하여 모든 게시물 출력 (최대 100개)
     * @param keyword 검색 키워드
     * @return 게시물 응답 리스트 (id, title, content)
     */
    @Override
    public List<BoardResponseDto> boardAllSearchByKeyword(String keyword) {

        log.info(keyword);

        List<Board> boards = boardQueryRepository.boardSearchByKeyword(keyword);

        log.info("board = {}", boards);

        return responseDtosCreate(boards);
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

    public static List<BoardResponseDto> responseDtosCreate(List<Board> boards) {
        return boards.stream().map(BoardResponseDto::from)
                .collect(toList());
    }
}
