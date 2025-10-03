package com.test.Member.dto;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Gender;
import com.test.Member.entity.Role;
import com.test.Member.entity.MemberRole; // 새로 추가
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverJoinDto {

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(max = 50)
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;

    @NotNull(message = "나이를 입력해주세요")
    private Integer age;

    @NotNull(message = "회원 유형을 선택해주세요")
    private MemberRole memberRole; // GIVER / TAKER

    @NotNull(message = "권한을 선택해주세요")
    private Role role; // USER / ADMIN

    @NotNull(message = "장애 유형을 선택해주세요")
    private DisabilityType disabilityType;
}