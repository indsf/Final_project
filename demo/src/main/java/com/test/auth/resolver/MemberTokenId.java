package com.test.auth.resolver;

import com.test.Member.entity.Role;
import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 파라미터에서 체크
@Retention(RetentionPolicy.RUNTIME) // 런타임 실행될때
public @interface MemberTokenId {
    boolean required() default true;
    Role[] allowedRoles() default {Role.ADMIN,Role.USER};
}
