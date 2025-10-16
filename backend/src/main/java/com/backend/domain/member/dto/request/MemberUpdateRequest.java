package com.backend.domain.member.dto.request;

public record MemberUpdateRequest(
        String email,
        String nickname
) {
}
