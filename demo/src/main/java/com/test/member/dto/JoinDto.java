package com.test.member.dto;


import com.test.member.entity.DisabilityType;
import com.test.member.entity.Gender;
import com.test.member.entity.MemberRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 50)
    private String name;

    @NotBlank(message = "닉네임을 입력해주세요")
    @Size(max = 50)
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 올바르지 않습니다")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S+$).{8,16}$", message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상 16자 이하로 입력해주세요.")
    private String password;

    @NotNull(message = "성별을 선택해주세요")
    private Gender gender;

    @NotNull(message = "나이를 입력해주세요")
    private int age;

    @NotNull(message = "장애 유형을 선택해주세요")
    private DisabilityType disabilityType;

    @NotNull(message = "회원 역할을 선택해주세요")
    private MemberRole memberRole; // GIVER / TAKER 선택
}
