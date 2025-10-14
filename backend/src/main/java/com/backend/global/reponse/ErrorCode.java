package com.backend.global.reponse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //회원
    CONFLICT_REGISTER("U001", HttpStatus.CONFLICT, "이미 가입된 회원입니다."),
    NOT_FOUND_MEMBER("U002", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    BAD_CREDENTIAL("U004", HttpStatus.UNAUTHORIZED, "아이디나 비밀번호가 틀렸습니다."),
    NOT_LOGIN_ACCESS("U007", HttpStatus.UNAUTHORIZED, "로그인되어 있지 않습니다. 로그인 해 주십시오."),

    //계획
    NOT_FOUND_PLAN("D001",HttpStatus.NOT_FOUND,"계획이 없습니다."),

    //여행지
    NOT_FOUND_PLACE("P001",HttpStatus.NOT_FOUND,"여행지를 찾을 수 없습니다."),

    //북마크
    NOT_FOUND_BOOKMARK("P001",HttpStatus.NOT_FOUND,"북마크를 찾을 수 없습니다."),

    //
    NOT_FOUND_REVIEW("R001",HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다.")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
