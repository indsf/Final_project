package com.test.member.exception;

import com.test.common.exception.BussinessException;


public class MemberNotFoundValidCheck extends BussinessException {
    public static final BussinessException bussinessException = new MemberNotFoundValidCheck();

    private MemberNotFoundValidCheck() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}
