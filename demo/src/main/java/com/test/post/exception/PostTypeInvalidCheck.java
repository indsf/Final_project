package com.test.post.exception;

import com.test.common.exception.BussinessException;

public class PostTypeInvalidCheck extends BussinessException {
    private static final BussinessException BUSSINESS_EXCEPTION = new PostTypeInvalidCheck();

    public PostTypeInvalidCheck() {
        super(PostErrorCode.POST_TYPE_INVALID);
    }
}
