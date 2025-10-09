package com.test.auth.config;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.entity.Member;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Member getCurrentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getMember();
        }
        throw new IllegalStateException("인증된 사용자 정보를 찾을 수 없습니다.");
    }
}
