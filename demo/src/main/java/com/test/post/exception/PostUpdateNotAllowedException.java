package com.test.post.exception;

import com.test.common.exception.BussinessException;

public class PostUpdateNotAllowedException extends BussinessException {

    public static final BussinessException EXCEPTION = new PostUpdateNotAllowedException();

    private PostUpdateNotAllowedException() {
        super(PostErrorCode.POST_UPDATE_NOT_ALLOWED);
    }
}
