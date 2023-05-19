package com.example.yun.dto.member;

import com.example.yun.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    private Long id;
    private String email;
    private LocalDateTime createTime;

    private MemberResponseDto(Long id, String email, LocalDateTime createTime) {
        this.id = id;
        this.email = email;
        this.createTime = createTime;
    }

    public static MemberResponseDto create(Member member) {
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getCreateTime());
    }
}
