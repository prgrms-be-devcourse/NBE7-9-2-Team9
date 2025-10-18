package com.backend.domain.auth.controller;

import com.backend.domain.auth.dto.reponse.TokenResponse;
import com.backend.domain.auth.service.AuthService;
import com.backend.domain.member.dto.request.MemberLoginRequest;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody MemberLoginRequest request) {
        TokenResponse tokenResponse = authService.login(request.memberId(), request.password());
        return ApiResponse.success(tokenResponse, "로그인 성공! 토큰 발급 완료");
    }

    //TODO: 토큰을 헤더로 보낼지, 바디로 보낼지 결정
    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reissue(@RequestHeader("Authorization") String refreshToken) {
        TokenResponse tokenResponse = authService.reissue(refreshToken);
        return ApiResponse.success(tokenResponse, "AccessToken 재발급 완료");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String accessToken) {
        authService.logout(accessToken);
        return ApiResponse.success(null, "로그아웃 완료");
    }
}
