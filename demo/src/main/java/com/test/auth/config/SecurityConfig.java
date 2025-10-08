package com.test.auth.config;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 웹 보안 설정 구성
public class SecurityConfig {

    //생성자 생성 호출시
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {}) // 필요 시 CORS 설정
                .csrf(csrf -> csrf.disable()) // ✅ CSRF 토큰 끄기
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 요청 오면 세션 생성
                )
                .authorizeHttpRequests(auth -> auth
                        // Swagger 허용
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html",
                                "/v3/api-docs/**", "/api-docs/json/**").permitAll()

                        // 회원가입/로그인/비번찾기 등은 공개
                        .requestMatchers("/api/user/login", "/api/user/signup", "/api/user/find-*").permitAll()

                        // 게시글 읽기만 공개, 나머진 인증 필요
                        .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/posts/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/posts/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/**").authenticated()

                        // 프로필/닉네임은 인증 필요
                        .requestMatchers("/api/user/profile", "/api/user/nickname").authenticated()

                        // 나머지는 전부 인증
                        .anyRequest().authenticated()
                )
                // formLogin 아예 제거 (REST API니까 불필요)
                .logout(l -> l
                        .logoutUrl("/api/user/logout")
                        .logoutSuccessHandler((req, res, auth) -> {
                            res.setStatus(HttpServletResponse.SC_OK);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("{\"success\":true}");
                        })
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                // 인증/권한 예외 JSON으로 반환
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("{\"success\":false,\"message\":\"UNAUTHENTICATED\"}");
                        })
                        .accessDeniedHandler((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json;charset=UTF-8");
                            res.getWriter().write("{\"success\":false,\"message\":\"FORBIDDEN\"}");
                        })
                );

        return http.build();
    }


    // 필터 체인 거친후 메니저한테 권한 위임
    // 로그인 권한 확인
    @Bean
    public AuthenticationManager authenticationManager (HttpSecurity security) throws Exception {
        AuthenticationManagerBuilder builder = security.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}