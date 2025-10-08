package com.test.common.exception;

public class BussinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BussinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
