package com.example.yun.service;

public interface GoodService {

    default void goodUp(Long memberId, Long boardId) {}
    default void goodDown(Long memberId, Long boardId) {}
}
