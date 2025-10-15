package com.backend.domain.member.dto.request;

public record MemberLoginRequest (
        String memberId,
        String password
){ }
