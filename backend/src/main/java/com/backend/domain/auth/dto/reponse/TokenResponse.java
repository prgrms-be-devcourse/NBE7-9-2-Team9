package com.backend.domain.auth.dto.reponse;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
