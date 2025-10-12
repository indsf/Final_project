package com.test.member.exception;

import com.test.common.exception.BussinessException;

public class DisabilityTypeValidCheck extends BussinessException {
    public static final BussinessException bussinessException = new DisabilityTypeValidCheck();

    private DisabilityTypeValidCheck() {
        super(MemberErrorCode.DISABILITY_INVALID_TYPE);
    }
}
