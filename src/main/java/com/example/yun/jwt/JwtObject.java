package com.example.yun.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtObject {

    private Long id;

    private JwtObject(Long id) {
        this.id = id;
    }

    public static JwtObject create(Long id) {
        return new JwtObject(id);
    }
}
