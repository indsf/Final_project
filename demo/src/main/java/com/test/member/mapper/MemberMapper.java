<<<<<<< HEAD:demo/src/main/java/com/test/member/mapper/MemberMapper.java
package com.test.member.mapper;
=======
package com.test.Member.mapper;

import com.test.Member.dto.JoinDto;
import com.test.Member.entity.Member;
import com.test.Member.entity.MemberRole;
import com.test.Member.entity.Role;
import com.test.Member.repository.MemberRepository;
>>>>>>> develop:demo/src/main/java/com/test/Member/mapper/MemberMapper.java

import com.test.member.dto.JoinDto;
import com.test.member.entity.Member;


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
                .memberRole(dto.getMemberRole())
                .role(dto.getRole())
                .build();
    }


}
