package com.example.yun.service.impl;

import com.example.yun.dto.BoardRequestDto;
import com.example.yun.repository.BoardRepository;
import com.example.yun.service.BoardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Nested
    @DisplayName("성공")
    class Success {

        @Test
        @DisplayName("게시물 등록")
        void createBoardTest() {

        }

        @AfterEach
        void initDB() {
            boardRepository.deleteAll();
        }
    }

    @Nested
    @DisplayName("실패")
    class Failed {

        @AfterEach
        void initDB() {
            boardRepository.deleteAll();
        }
    }
}