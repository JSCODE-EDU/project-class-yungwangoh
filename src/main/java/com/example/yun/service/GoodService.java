package com.example.yun.service;

public interface GoodService {

    default void goodUp(String jwt, Long boardId) {}
    default void goodDown(String jwt, Long boardId) {}
}
