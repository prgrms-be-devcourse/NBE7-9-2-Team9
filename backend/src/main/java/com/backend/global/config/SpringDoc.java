package com.backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        scheme = "BEARER"
)
@Configuration
@OpenAPIDefinition(info = @Info(title = "파이팀 팀 프로젝트 API 서버", version = "beta",description = "데브코스 백엔드 7회차 9기 2차 프로젝트 9팀 파이팀 API 명세입니다."))
public class SpringDoc {
    @Bean
    public GroupedOpenApi groupApiV1() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi(){
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/api/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi(){
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi bookmarkApi(){
        return GroupedOpenApi.builder()
                .group("bookmark")
                .pathsToMatch("/api/bookmarks/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryApi(){
        return GroupedOpenApi.builder()
                .group("category")
                .pathsToMatch("/api/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi memberApi(){
        return GroupedOpenApi.builder()
                .group("member")
                .pathsToMatch("/api/members/**")
                .build();
    }

    @Bean
    public GroupedOpenApi placeApi(){
        return GroupedOpenApi.builder()
                .group("place")
                .pathsToMatch("/api/place/**")
                .build();
    }

    @Bean
    public GroupedOpenApi planApi(){
        return GroupedOpenApi.builder()
                .group("plan")
                .pathsToMatch("/api/plan/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApi(){
        return GroupedOpenApi.builder()
                .group("review")
                .pathsToMatch("/api/review/**")
                .build();
    }
}
