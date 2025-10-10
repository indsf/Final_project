package com.test.Member.mapper;

import com.test.Member.dto.JoinDto;
import com.test.Member.entity.Member;
import com.test.Member.repository.MemberRepository;



public class MemberMapper {
    private MemberMapper(){};


    //일반 회원 가입
    public static Member createMember(JoinDto dto, String encodedPassword) {
        return Member.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encodedPassword)
                .gender(dto.getGender())
                .age(dto.getAge())
                .disabilityType(dto.getDisabilityType())
                .build();
    }


}
