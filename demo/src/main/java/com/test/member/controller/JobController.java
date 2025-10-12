package com.test.member.controller;


import com.test.member.dto.JobRequestDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")

public class JobController {
    //입력값 테스트
    @PostMapping("/recommend")
    public JobRequestDto recommendJob(@RequestBody JobRequestDto jobRequest) {
        // 현재는 FastAPI/AI 연동 전 → 입력값 그대로 반환
        return jobRequest;
    }

     /**
     * FastAPI 연동
     *
     * 1. RestTemplate 사용해서 FastAPI 서버에 POST 요청 전송
     *    - URL: http://localhost:8000/recommend (예시)
     *    - Body: jobRequest (JSON)
     *
     * 2. FastAPI 서버에서는 AI 모델 실행
     *    - 입력된 조건(region, disability, pay)에 따라 추천 알고리즘 적용
     *    - 실시간 구직정보 크롤링/DB 데이터 활용
     *    - 추천 결과 JSON 응답
     *
     * 3. 스프링부트에서 FastAPI 응답을 받아 JobResponseDto로 매핑
     *
     * 4. 최종적으로 클라이언트(웹/앱/Swagger)에 추천 결과 반환
     */

    // 예시 (FastAPI 호출 코드, 실제 연동시 활성화)
        /*
        RestTemplate restTemplate = new RestTemplate();
        String fastApiUrl = "http://localhost:8000/recommend";
        JobResponseDto response = restTemplate.postForObject(fastApiUrl, jobRequest, JobResponseDto.class);
        return response;
        */

}
