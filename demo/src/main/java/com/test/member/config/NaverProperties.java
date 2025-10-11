package com.test.member.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "naver")
public class NaverProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String requestTokenUri;

    public NaverProperties() {} // 기본 생성자 필요

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getRedirectUri() { return redirectUri; }
    public void setRedirectUri(String redirectUri) { this.redirectUri = redirectUri; }

    public String getRequestTokenUri() { return requestTokenUri; }
    public void setRequestTokenUri(String requestTokenUri) { this.requestTokenUri = requestTokenUri; }

    public String getRequestURL(String code) {
        return requestTokenUri + "?grant_type=authorization_code&client_id=" + clientId +
                "&client_secret=" + clientSecret + "&code=" + code;
    }
}
