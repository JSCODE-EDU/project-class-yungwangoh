package com.example.yun.domain.board;

import com.example.yun.exception.StringValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    @Nested
    @DisplayName("제목")
    class Email {

        @DisplayName("제목이 공백이거나 길이가 15자를 넘어가는 경우")
        @ParameterizedTest
        @ValueSource(ints = {0, 20})
        void titleLengthOver(int num) {
            // given
            String title = " ".repeat(num);
            String content = "ㅎㅇ";

            // when

            // then
            assertThatThrownBy(() -> Board.create(title, content))
                    .isInstanceOf(StringValidationException.class);
        }
    }

    @Nested
    @DisplayName("내용")
    class Content {

        @DisplayName("내용이 공백이거나 길이가 1000자를 넘어가는 경우")
        @ParameterizedTest
        @ValueSource(ints = {0, 1100})
        void contentLengthOver(int num) {
            // given
            String title = "ㅎㅇ";
            String content = " ".repeat(num);

            // when

            // then
            assertThatThrownBy(() -> Board.create(title, content))
                    .isInstanceOf(StringValidationException.class);
        }

    }

}