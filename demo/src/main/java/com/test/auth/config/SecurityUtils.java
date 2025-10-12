package com.test.auth.config;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.entity.Member;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static Member getCurrentMember() {
        // 현재 인증 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //  인증 여부 및 상태 출력 (디버깅용)
        System.out.println("Principal = " + authentication.getPrincipal()); // 로그인한 사용자 객체
        System.out.println("Authorities = " + authentication.getAuthorities()); // 권한 목록
        System.out.println("Authenticated = " + authentication.isAuthenticated()); // 인증 성공 여부

        //  Principal이 CustomUserDetails인지 확인
        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return userDetails.getMember();
        }

        throw new IllegalStateException("인증된 사용자 정보를 찾을 수 없습니다.");
    }

    public static Optional<Member> getCurrentMemberOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            return Optional.ofNullable(userDetails.getMember());
        }
        return Optional.empty();
    }

    /** 익명/널 안전: 현재 사용자 ID(Optional) */
    public static Optional<Long> currentMemberId() {
        return getCurrentMemberOptional().map(Member::getId);
    }

    /** 익명/널 안전: 현재 사용자 ID (없으면 null 반환) */
    public static Long currentMemberIdOrNull() {
        return currentMemberId().orElse(null);
    }
}
