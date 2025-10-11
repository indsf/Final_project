package com.test.member.exception;

import com.test.common.exception.BussinessException;

public class INVALID_PASSWORD_OR_EMAIL extends BussinessException {

    public static final BussinessException bussinessException = new INVALID_PASSWORD_OR_EMAIL();

    public INVALID_PASSWORD_OR_EMAIL() {
        super(MemberErrorCode.INVALID_PASSWORD_OR_EMAIL);
    }
}
