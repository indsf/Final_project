package com.test.post.exception;

import com.test.common.exception.BussinessException;

public class PostNotFoundException extends BussinessException {
    public static final BussinessException EXCEPTION = new PostNotFoundException();

    private PostNotFoundException() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
