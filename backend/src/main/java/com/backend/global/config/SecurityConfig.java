package com.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth
                        /*.requestMatchers(
                                "/api/members/signup",
                                "/api/members/login",
                                "/h2-console/**"
                        ).permitAll()

                        // TODO: 관리자 전용 API (추후 적용)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        */

                        .anyRequest().permitAll()
                )

                // H2 콘솔 접근 허용
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }
}
