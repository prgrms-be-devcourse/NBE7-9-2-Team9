package com.backend.domain.auth.service;

import com.backend.domain.auth.dto.reponse.TokenResponse;
import com.backend.domain.auth.entity.RefreshToken;
import com.backend.domain.auth.repository.RefreshTokenRepository;
import com.backend.domain.member.dto.request.MemberLoginRequest;
import com.backend.domain.member.dto.response.MemberResponse;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.service.MemberService;
import com.backend.global.exception.BusinessException;
import com.backend.global.jwt.JwtTokenProvider;
import com.backend.global.jwt.TokenStatus;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 인증 서비스 (JWT 발급, 재발급, 로그아웃)
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /** 로그인: AccessToken + RefreshToken 발급 */
    @Transactional
    public TokenResponse login(String loginId, String password) {

        MemberResponse memberResponse = memberService.login(
                new MemberLoginRequest(loginId, password)
        );

        Long memberPk = memberResponse.id();

        String accessToken = jwtTokenProvider.generateAccessToken(memberResponse.id(), memberResponse.role());
        String refreshToken = jwtTokenProvider.generateRefreshToken(memberResponse.id(), memberResponse.role());

        saveOrUpdateRefreshToken(memberPk, refreshToken);

        log.info("[Auth] 로그인 성공: memberPk={}, issuedAt={}", memberPk, LocalDateTime.now());

        return new TokenResponse(accessToken, refreshToken);
    }

    /** AccessToken 재발급 */
    @Transactional
    public TokenResponse reissue(String refreshTokenHeader) {

        // "Bearer" 접두사 제거
        String token = extractToken(refreshTokenHeader);

        // RefreshToken 유효성 검증
        validateTokenStatus(token);

        // 1. RefreshToken에서 memberPk 추출
        Long memberPk = jwtTokenProvider.getMemberIdFromToken(token);

        // 2. DB에 저장된 RefreshToken 검증 (일단 반환하게 만들었음)
        RefreshToken savedToken = getValidatedRefreshToken(token, memberPk);

        // 3. RefreshToken에는 role 정보가 없기 때문에 MemberService로부터 다시 조회 필요
        // TODO: 맴버 객체에 직접 접근하는 게 맞을까? (memberResponse도 있음)
        Member member = memberService.findByIdEntity(memberPk);

        // 4. 새 AccessToken 발급
        String newAccessToken = jwtTokenProvider.generateAccessToken(memberPk, member.getRole());

        log.info("[Auth] AccessToken 재발급 완료: memberPk={}, reissuedAt={}", memberPk, LocalDateTime.now());
        return new TokenResponse(newAccessToken, token);
    }

    @Transactional
    public void logout(String accessTokenHeader) {

        // 1. Bearer 제거
        String token = extractToken(accessTokenHeader);

        // 2. DB에서 해당 토큰 보유한 회원 조회
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));

        // 3. RefreshToken 삭제
        refreshTokenRepository.deleteByMemberPk(refreshToken.getMemberPk());

        log.info("[Auth] 로그아웃 완료: memberPk={}, deletedAt={}", refreshToken.getMemberPk(), LocalDateTime.now());
    }

    /**
     * RefreshToken 생성 or 갱신
     */
    @Transactional
    private void saveOrUpdateRefreshToken(Long memberPk, String refreshToken) {
        LocalDateTime expiryTime = LocalDateTime.now()
                .plusSeconds(jwtTokenProvider.getRefreshTokenExpireTime());

        RefreshToken token = refreshTokenRepository.findByMemberPk(memberPk)
                .orElseGet(() -> RefreshToken.builder()
                        .memberPk(memberPk)
                        .issuedAt(LocalDateTime.now())
                        .build());

        token.updateToken(refreshToken, expiryTime);
        refreshTokenRepository.save(token);
    }

    // === 공통 유틸 메서드 === //

    /** 토큰에서 memberId 가져오기 */
    public Long getMemberId(String accessTokenHeader) {
        String token = extractToken(accessTokenHeader);
        return jwtTokenProvider.getMemberIdFromToken(token);
    }

    /** Bearer 접두사 제거 */
    private String extractToken(String headerValue) {
        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.TOKEN_NOT_FOUND);
        }
        return headerValue.replace("Bearer ", "").trim();
    }

    /** RefreshToken DB 검증 */
    private RefreshToken getValidatedRefreshToken(String refreshToken, Long memberPk) {
        RefreshToken savedToken = refreshTokenRepository.findByMemberPk(memberPk)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.MISMATCH_REFRESH_TOKEN);
        }
        return savedToken;
    }

    /** 토큰 상태 검증 (TokenStatus 기반) */
    private void validateTokenStatus(String token) {
        TokenStatus status = jwtTokenProvider.validateTokenStatus(token);

        switch (status) {
            case EXPIRED -> throw new BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN);
            case INVALID -> throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
            case VALID -> log.debug("[Auth] 토큰 유효성 검증 완료");
        }
    }

}

