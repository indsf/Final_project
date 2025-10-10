package com.test.fastapi;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class RecommendService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FASTAPI_URL = "http://127.0.0.1:8000/recommend";

    public Map<String, Object> getRecommendations(String region, String jobType) {
        // FastAPI에 보낼 JSON 데이터
        Map<String, String> requestBody = Map.of(
                "region", region,
                "job_type", jobType
        );

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // FastAPI POST 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL, entity, Map.class);

        return response.getBody();
    }
}
