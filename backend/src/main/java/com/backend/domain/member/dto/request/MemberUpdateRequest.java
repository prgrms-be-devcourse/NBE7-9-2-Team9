package com.backend.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberUpdateRequest(

        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank
        String nickname
) { }
