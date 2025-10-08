package com.test.post.exception;

import com.test.common.exception.BussinessException;

public class ScheduleTypeInvalidCheck extends BussinessException {
    public static final BussinessException BUSSINESS_EXCEPTION = new ScheduleTypeInvalidCheck();

    public ScheduleTypeInvalidCheck() {
        super(PostErrorCode.SCHEDULE_TYPE_INVALID);
    }
}
