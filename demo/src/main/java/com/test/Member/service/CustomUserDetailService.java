package com.test.Member.service;

import com.test.Member.detail.CustomUserDetails;
import com.test.Member.entity.Member;
import com.test.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    //*
    // 로그인 흐름 정리
    //클라이언트가 /login API에 email, password 보냄
    //Spring Security → CustomUserDetailsService.loadUserByUsername(email) 호출
    //DB에서 Member 찾고 → CustomUserDetails 로 감쌈
    //시큐리티 내부에서 passwordEncoder.matches(요청 비번, DB비번) 비교
    //성공 → 세션/토큰 발급, 실패 → 401 Unauthorized
    //*

    //로그인 시 아이디 → DB 조회 → UserDetails 변환
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member m = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("not found :" + email));
        return new CustomUserDetails(m);
    }
}
