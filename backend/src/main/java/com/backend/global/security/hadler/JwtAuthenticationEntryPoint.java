package com.backend.global.security.hadler;

import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근했을 때 (401 Unauthorized)
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        log.warn("인증되지 않은 요청입니다. URI: {}", request.getRequestURI());
        throw new BusinessException(ErrorCode.UNAUTHORIZED_REQUEST);
    }
}