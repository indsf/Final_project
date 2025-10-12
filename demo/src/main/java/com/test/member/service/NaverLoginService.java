package com.test.member.service;

import com.test.member.config.NaverProperties;
import com.test.member.dto.NaverTokenResponseDto;
import com.test.member.dto.NaverUserDto;
import com.test.member.entity.Member;
import com.test.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NaverLoginService {

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final NaverProperties naverProperties;

    /** 네이버 인가 코드로 로그인 */
    public Member loginByCode(String code, String state) {
        String accessToken = requestAccessToken(code, state);
        NaverUserDto.NaverUserDetail profile = requestProfile(accessToken);

        // DB에 회원이 있으면 바로 반환
        return memberRepository.findByEmail(profile.getEmail())
                .orElseGet(() -> {
                    // 신규회원 → 최소 정보만 가지고 반환
                    return Member.builder()
                            .email(profile.getEmail())
                            .name(profile.getName() != null ? profile.getName() : "네이버유저")
                            .nickname(profile.getName() != null ? profile.getName() : "네이버유저")
                            .role(null)
                            .build();
                });
    }

    private String requestAccessToken(String code, String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverProperties.getClientId());
        params.add("client_secret", naverProperties.getClientSecret());
        params.add("code", code);
        params.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<NaverTokenResponseDto> response = restTemplate.postForEntity(
                "https://nid.naver.com/oauth2.0/token",
                request,
                NaverTokenResponseDto.class
        );

        NaverTokenResponseDto tokenResponse = response.getBody();
        if (tokenResponse == null || tokenResponse.getAccessToken() == null) {
            throw new IllegalArgumentException("네이버 토큰 발급 실패");
        }

        return tokenResponse.getAccessToken();
    }

    private NaverUserDto.NaverUserDetail requestProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<NaverUserDto> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                NaverUserDto.class
        );

        NaverUserDto userResponse = response.getBody();
        if (userResponse == null || userResponse.getNaverUserDetail() == null) {
            throw new IllegalArgumentException("네이버 프로필 조회 실패");
        }
        return userResponse.getNaverUserDetail();
    }
}
