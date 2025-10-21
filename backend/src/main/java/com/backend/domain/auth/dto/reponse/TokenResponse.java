package com.backend.domain.auth.dto.reponse;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        long refreshTokenMaxAge
) {
    public static TokenResponse of(String accessToken, String refreshToken, long refreshTokenMaxAge) {
        return new TokenResponse(accessToken, refreshToken, refreshTokenMaxAge);
    }
}
