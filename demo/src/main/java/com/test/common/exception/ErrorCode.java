package com.test.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();
    String getCode();

    HttpStatus HttpStatus();

    String getMessage();
}
