package com.test.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String SESSION_SCHEME = "sessionCookie";  // JSESSIONID
    private static final String CSRF_SCHEME    = "xsrfHeader";     // X-XSRF-TOKEN

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);

        SecurityScheme sessionCookie = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("JSESSIONID")
                .description("세션 쿠키");

        SecurityScheme xsrfHeader = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-XSRF-TOKEN")
                .description("CSRF 방어용 토큰 헤더 (쿠키 XSRF-TOKEN과 동일 값)");

        SecurityRequirement requirement = new SecurityRequirement()
                .addList(SESSION_SCHEME)
                .addList(CSRF_SCHEME);

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("API 문서")
                        .version("v1")
                        .description("세션 + CSRF 기반 인증"))
                .components(new Components()
                        .addSecuritySchemes(SESSION_SCHEME, sessionCookie)
                        .addSecuritySchemes(CSRF_SCHEME, xsrfHeader))
                .addSecurityItem(requirement);
    }
}
