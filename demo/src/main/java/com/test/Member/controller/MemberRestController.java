package com.test.Member.controller;

import com.test.Member.dto.FindDto;
import com.test.Member.dto.JoinDto;
import com.test.Member.dto.NickNameDto;
import com.test.Member.dto.ProfileResDto;
import com.test.Member.entity.Member;
import com.test.Member.service.MemberService;
import com.test.auth.config.SecurityUtils;
import com.test.common.exception.BussinessException;
import com.test.common.exception.ErrorCodeDetail;
import com.test.common.exception.ErrorResponse;
import com.test.utils.api.ApiError;
import com.test.utils.api.ApiResponse;
import com.test.utils.api.ApiResponse.CustomBody;
import com.test.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "회원 관련 REST API")
public class MemberRestController {

    private final MemberService memberService;

    /** 회원가입 */
    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임 등으로 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ApiResponse<CustomBody<Member>> signup(
            @Parameter(description = "회원가입 정보 DTO") @RequestBody @Valid JoinDto joinDto) {

        try {
            Member member = memberService.signup(joinDto); // signup 수정 필요: Member 반환
            CustomBody<Member> body = new CustomBody<>(true, member, null);
            return new ApiResponse<>(body, HttpStatus.CREATED);
        } catch (BussinessException e) {
            CustomBody<Member> body = new CustomBody<>(false, null, null); // 필요 시 ErrorResponse 확장 가능
            return new ApiResponse<>(body, HttpStatus.BAD_REQUEST);
        }
    }

    /** 프로필 조회 */
    @Operation(summary = "프로필 조회", description = "로그인한 회원의 프로필 정보를 조회합니다.")
    @GetMapping("/profile")
    public ApiResponse<CustomBody<Member>> profile() {
        try {
            Member member = SecurityUtils.getCurrentMember();
            return ApiResponseGenerator.success(member, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ApiResponseGenerator.fail(ErrorCodeDetail.UNAUTHORIZED);
        }
    }


    /** 닉네임 변경 */
    @Operation(summary = "닉네임 변경", description = "회원 ID와 새 닉네임으로 닉네임을 변경합니다.")
    @PutMapping("/nickname")
    public ApiResponse<CustomBody<Member>> updateNickname(@RequestBody @Valid NickNameDto dto) {
        Member updated = memberService.updateNickname(dto.getOldNickname(), dto.getNewNickname());
        return ApiResponseGenerator.success(updated, HttpStatus.OK);
    }

    /** 아이디 찾기 */
    @Operation(summary = "아이디 찾기", description = "사용자가 입력한 비밀번호를 통해 이메일(아이디)을 조회합니다.")
    @PostMapping("/find-id")
    public ApiResponse<ApiError.CustomBody<String>> findId(@RequestBody @Valid FindDto request) {
        String password = request.getValue();
        String email = memberService.findEmailByPassword(password);

        if (email == null) {
            ApiError.CustomBody<String> body = new ApiError.CustomBody<>(false, null, "일치하는 회원이 없습니다.");
            return new ApiResponse<>(body, HttpStatus.NOT_FOUND);
        }

        ApiError.CustomBody<String> body = new ApiError.CustomBody<>(true, email, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    /** 비밀번호 찾기 */
    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 찾기", description = "회원 이메일로 비밀번호를 조회합니다.")
    public ApiResponse<ApiError.CustomBody<String>> findPassword(@RequestBody @Valid FindDto request) {
        String email = request.getValue();
        String password = memberService.findPasswordByEmail(email);

        if (password == null) {
            ApiError.CustomBody<String> body = new ApiError.CustomBody<>(false, null, "일치하는 회원이 없습니다.");
            return new ApiResponse<>(body, HttpStatus.NOT_FOUND);
        }

        ApiError.CustomBody<String> body = new ApiError.CustomBody<>(true, password, null);
        return new ApiResponse<>(body, HttpStatus.OK);
    }

}