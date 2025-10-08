package com.test.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
public class SwaggerController {

    @GetMapping("/api/csrf")
    public Map<String, String> csrf(CsrfToken token) {
        return Map.of(
                "headerName", token.getHeaderName(),   // 보통 "X-XSRF-TOKEN"
                "parameterName", token.getParameterName(), // 보통 "_csrf"
                "token", token.getToken()
        );
    }
}

