package com.example.yun.env;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootTest
public class EnvironmentTest {

    @Autowired
    private Environment environment;

    @Test
    @DisplayName("yml 환경 변수 테스트")
    void environmentTest() {
        Assertions.assertEquals("dev", environment.getActiveProfiles()[0]);
        System.out.println(Arrays.toString(environment.getActiveProfiles()));
    }
}
