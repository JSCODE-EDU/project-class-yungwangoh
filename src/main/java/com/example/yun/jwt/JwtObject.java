package com.example.yun.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtObject {

    private Long id;
    private String email;

    private JwtObject(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public static JwtObject create(Long id, String email) {
        return new JwtObject(id, email);
    }
}
