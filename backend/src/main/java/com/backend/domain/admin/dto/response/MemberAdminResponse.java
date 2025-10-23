package com.backend.domain.admin.dto.response;

import com.backend.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record MemberAdminResponse(

        Long id,

        @Email
        String email,

        @NotBlank
        String nickname,

        String role
        // LocalDateTime createdAt
) {
    public static MemberAdminResponse from(Member member) {
        return new MemberAdminResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getRole().name()
                // member.getCreatedAt()
        );
    }
}
