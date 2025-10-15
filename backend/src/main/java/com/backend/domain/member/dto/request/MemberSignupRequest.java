package com.backend.domain.member.dto.request;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.entity.Role;

public record MemberSignupRequest(
        String memberId,
        String password,
        String email,
        String nickname
) {
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .memberId(memberId)
                .password(encodedPassword)
                .email(email)
                .nickname(nickname)
                .role(Role.USER)
                .build();
    }
}
