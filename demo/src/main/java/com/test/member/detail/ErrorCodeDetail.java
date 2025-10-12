package com.test.member.detail;

import com.test.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ErrorCodeDetail implements ErrorCode {

    // ✅ 인증 관련
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "UNAUTHENTICATED", "로그인이 필요합니다."),
    INVALID_AUTH_OBJECT(HttpStatus.UNAUTHORIZED, "INVALID_AUTH_OBJECT", "인증 객체가 유효하지 않습니다."),

    // ✅ 회원 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "비밀번호가 일치하지 않습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "LOGIN_FAILED", "로그인에 실패했습니다."),

    // ✅ 접근 권한
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "접근 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCodeDetail(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus HttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
