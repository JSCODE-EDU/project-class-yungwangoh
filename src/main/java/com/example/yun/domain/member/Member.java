package com.example.yun.domain.member;

import com.example.yun.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private Email email;
    private Password password;

    private Member(final String email, final String password) {
        this.email = Email.emailCreate(email);
        this.password = Password.pwdCreate(password);
    }

    public static Member create(String email, String password) {
        return new Member(email, password);
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getEmail() {
        return email.getEmail();
    }
}
