package com.test.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCodeDetail implements ErrorCode{

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "E001", "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "존재하지 않는 이메일입니다."),
    LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E003", "로그인 실패"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "E004","로그인 해주세요");

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
        return null;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
