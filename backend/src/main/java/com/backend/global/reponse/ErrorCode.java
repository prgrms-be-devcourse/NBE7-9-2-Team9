package com.backend.global.reponse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 회원가입 - Member
    DUPLICATE_MEMBER_ID("M003", HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    DUPLICATE_EMAIL("M004", HttpStatus.CONFLICT, "이미 가입된 이메일입니다."),
    DUPLICATE_NICKNAME("M005", HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다."),

    CONFLICT_REGISTER("M001", HttpStatus.CONFLICT, "이미 가입된 회원입니다."),

    MEMBER_NOT_FOUND("M006", HttpStatus.NOT_FOUND,"존재하지 않는 회원입니다."),
    INVALID_PASSWORD("M007", HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."),
    ALREADY_DELETED_MEMBER("M008", HttpStatus.BAD_REQUEST, "이미 탈퇴된 회원입니다."),

    //계획
    NOT_FOUND_PLAN("D001",HttpStatus.NOT_FOUND,"계획이 없습니다."),
    NOT_VALID_DATE("D002",HttpStatus.BAD_REQUEST,"입력된 날짜가 유효하지 않습니다."),

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
