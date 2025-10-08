package com.test.Member.exception;

import com.test.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND("M001", HttpStatus.NOT_FOUND, "존재하지 않는 회원입니당"),
    MEMBER_EMAIL_ALREADY_EXISTS("M002", HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니당"),
    INVALID_PASSWORD_OR_EMAIL("M003", HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니당"),
    MEMBER_NICKNAME_ALREADY_EXISTS("M004", HttpStatus.BAD_REQUEST, "이미 사용중인 닉네임입니당"),
    DISABILITY_INVALID_TYPE("M005", HttpStatus.BAD_REQUEST, "유효하지 않은 장애 유형입니당"),
    ;

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    MemberErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus HttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getCode() {
        return code;
    }
}
