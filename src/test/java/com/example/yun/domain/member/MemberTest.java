package com.example.yun.domain.member;

import com.example.yun.exception.StringValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Nested
    @DisplayName("이메일")
    class Email {

        @Nested
        @DisplayName("유효성")
        class Validation {

            @Test
            @DisplayName("정상적인 이메일")
            void passwordCheck() {
                // given
                String email = "qwer1234@naver.com";
                String pwd = "qwer1234@A";

                // when
                Member member = Member.create(email, pwd);

                // then
                assertThat(member.getEmail()).isEqualTo(email);
                assertThat(member.getPassword()).isEqualTo(pwd);
            }

            @Test
            @DisplayName("@가 빠진 경우")
            void emailCheck() {
                // given
                String email = "qwer1234naver.com";
                String pwd = "qwer1234@A";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);
            }

            @Test
            @DisplayName("공백이 있는 경우")
            void emailSpaceCheck() {
                // given
                String email = "qwer123 @naver.com";
                String pwd = "qwer1234@A";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);
            }

            @Test
            @DisplayName("@가 2개 이상일 경우")
            void emailMissMatchCheck() {
                // given
                String email = "qwer1234@@naver.com";
                String pwd = "qwer1234@A";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);

            }
        }
    }

    @Nested
    @DisplayName("비밀번호")
    class Password {

        @Nested
        @DisplayName("유효성")
        class Validation {

            @Test
            @DisplayName("정상적인 비밀번호")
            void passwordCheck() {
                // given
                String email = "qwer1234@naver.com";
                String pwd = "qwer1234@A";

                // when
                Member member = Member.create(email, pwd);

                // then
                assertThat(member.getEmail()).isEqualTo(email);
                assertThat(member.getPassword()).isEqualTo(pwd);
            }

            @Test
            @DisplayName("8 ~ 15자 사이 체크")
            void passwordLengthCheck() {
                // given
                String email = "qwer1234@naver.com";
                String pwd = "dd12@";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);
            }

            @Test
            @DisplayName("비밀번호는 특수 문자 1개 이상 대문자 1개이상 숫자 1개 이상 포함하고 소문자로 이루어져야 한다.")
            void emailValidationCheck() {
                // given
                String email = "qwer1234@naver.com";
                String pwd = "qwerqwerqwer";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);
            }

            @Test
            @DisplayName("공백 체크")
            void passwordSpaceCheck() {
                // given
                String email = "qwer1234@naver.com";
                String pwd = "qwerqwerq er";

                // when

                // then
                assertThatThrownBy(() -> Member.create(email, pwd))
                        .isInstanceOf(StringValidationException.class);
            }
        }
    }
}