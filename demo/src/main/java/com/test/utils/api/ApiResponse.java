package com.test.utils.api;

// JSON 직렬화 시 null 값은 포함하지 않도록 하기 위한 어노테이션
import com.fasterxml.jackson.annotation.JsonInclude;

// 롬복: 모든 필드를 파라미터로 받는 생성자를 자동 생성
import com.test.common.exception.ErrorResponse;
import lombok.AllArgsConstructor;
// 롬복: 모든 필드에 대해 getter 자동 생성
import lombok.Getter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
// 스프링에서 제공하는 응답 객체, HTTP 응답 전체(헤더, 바디, 상태코드)를 담을 수 있음
import org.springframework.http.ResponseEntity;
// 스프링 6에서 제공하는 표준 에러 응답 인터페이스

import java.io.Serializable;

@Getter // 클래스의 모든 필드에 대해 getter 메서드를 자동 생성
public class ApiResponse<B> extends ResponseEntity<B> { // ResponseEntity를 상속 → 프로젝트 표준 응답 포맷 정의

    // body + status 로 응답을 생성할 수 있는 생성자
    public ApiResponse(B body, HttpStatus status) {
        super(body, status);
    }

    // body + 미디어타입 + status 로 응답 생성
    public ApiResponse(B body, MediaType mediaType, HttpStatus status) {
        super(body, status);
        // 생성된 ResponseEntity의 header에 Content-Type 설정
        this.getHeaders().setContentType(mediaType);
    }

    // body + headers + status 로 응답 생성
    public ApiResponse(B body, HttpHeaders headers, HttpStatus status) {
        super(body, headers, status);
    }

    // 내부 정적 클래스: 실제로 API에서 내려보낼 응답 Body 구조를 정의
    @Getter // 필드 getter 자동 생성
    @AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 자동 생성
    @JsonInclude // JSON 직렬화 시 null 값은 생략
    public static class CustomBody<D> implements Serializable { // 직렬화 가능해야 네트워크 전송/저장 가능
        private Boolean success;     // 요청 성공 여부 (true/false)
        private D data;              // 성공 시 내려줄 데이터 (제네릭으로 유연하게)
        private ErrorResponse error; // 실패 시 내려줄 에러 응답

    }
}
