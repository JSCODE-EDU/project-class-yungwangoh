package com.example.yun.repository;

import com.example.yun.domain.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    private Board board;
    private final String title = "안녕하세요";
    private final String content = "안녕하십니까";

    @BeforeEach
    void init() {
        board = new Board(title, content);
    }
}