package com.example.yun.repository.board;

import com.example.yun.domain.board.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
}
