package com.test.Member.controller;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.entity.Member;
import com.test.auth.config.SecurityUtils;
import com.test.common.exception.ErrorCodeDetail;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Operation(summary = "현재 로그인된 사용자 정보 반환", description = "세션에서 현재 로그인 중인 사용자를 반환합니다.")
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session, Authentication auth) {
        // 세션/스프링시큐리티 둘 다 가능. 편한 쪽 택1 또는 병행
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
        CustomUserDetails cud = (CustomUserDetails) auth.getPrincipal();
        // 필요한 최소 정보만 내려주기
        Map<String,Object> me = Map.of(
                "member", cud.getMember(),
                "email", cud.getUsername()
        );
        return ResponseEntity.ok(me);
    }
}
