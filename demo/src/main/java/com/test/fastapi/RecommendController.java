package com.test.fastapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "FastAPI 추천 테스트", description = "FastAPI 서버와 통신하여 추천 결과를 콘솔에 출력")
@RestController
@RequestMapping("/api")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @Operation(
            summary = "추천 테스트 실행",
            description = "입력한 지역과 직종 정보를 FastAPI로 전송하고, 추천 결과를 콘솔에 출력합니다."
    )
    @GetMapping("/test-recommend")
    public String testRecommend(
            @Parameter(description = "지역명 (예: 부산, 서울 등)", example = "부산")
            @RequestParam(name = "region", defaultValue = "부산") String region,

            @Parameter(description = "직종명 (예: 식품 제조, 서비스 등)", example = "식품 제조")
            @RequestParam(name = "jobType", defaultValue = "식품 제조") String jobType
    ) {
        System.out.println("===============================================");
        System.out.println("✅ [Spring] FastAPI에 추천 요청 전달 중...");
        Map<String, Object> result = recommendService.getRecommendations(region, jobType);
        System.out.println("✅ [Spring] FastAPI 응답 수신 완료");
        System.out.println("-----------------------------------------------");

        if (result == null) {
            System.out.println("🚨 FastAPI로부터 응답이 없습니다.");
            return "❌ FastAPI 응답 없음 (콘솔 로그 확인)";
        }

        // 요청 정보 출력
        System.out.println("📍 요청 정보:");
        System.out.println("지역: " + result.get("region"));
        System.out.println("직종: " + result.get("job_type"));
        System.out.println();

        // 추천 결과 파싱
        Object recsObj = result.get("recommendations");
        if (recsObj instanceof List<?>) {
            List<?> recs = (List<?>) recsObj;
            System.out.println("📊 [추천 결과]");
            int rank = 1;
            for (Object itemObj : recs) {
                if (itemObj instanceof Map<?, ?> rec) {
                    // ✅ FastAPI의 영문 key 매핑
                    String company = String.valueOf(rec.get("company_name"));
                    String address = String.valueOf(rec.get("address"));

                    System.out.printf("%d. %s — %s%n", rank++, company, address);
                }
            }
        } else {
            System.out.println("추천 결과를 파싱할 수 없습니다: " + recsObj);
        }

        System.out.println("===============================================");
        return "✅ FastAPI 추천 결과가 콘솔에 출력되었습니다.";
    }
}


//package com.test.fastapi;
//
//import io.swagger.v3.oas.annotations.Operation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//        import java.util.*;
//
//@RestController
//@RequestMapping("/api")
//public class RecommendController {
//
//    @Autowired
//    private RecommendService recommendService;
//
//    @Operation(summary = "추천 결과 조회 (페이지네이션 포함)")
//    @GetMapping("/recommend")
//    public Map<String, Object> getRecommendations(
//            @RequestParam String region,
//            @RequestParam String jobType,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        // FastAPI에 전체 추천 요청
//        Map<String, Object> result = recommendService.getRecommendations(region, jobType);
//
//        if (result == null || !result.containsKey("recommendations")) {
//            return Map.of("message", "추천 결과 없음");
//        }
//
//        // 전체 추천 결과 리스트
//        List<Map<String, Object>> allRecs = (List<Map<String, Object>>) result.get("recommendations");
//
//        // 페이지 계산
//        int totalItems = allRecs.size();
//        int totalPages = (int) Math.ceil((double) totalItems / size);
//        int fromIndex = (page - 1) * size;
//        int toIndex = Math.min(fromIndex + size, totalItems);
//
//        List<Map<String, Object>> pageContent = new ArrayList<>();
//        if (fromIndex < totalItems) {
//            pageContent = allRecs.subList(fromIndex, toIndex);
//        }
//
//        // JSON 응답 구성
//        Map<String, Object> response = new LinkedHashMap<>();
//        response.put("region", region);
//        response.put("job_type", jobType);
//        response.put("page", page);
//        response.put("size", size);
//        response.put("total_pages", totalPages);
//        response.put("total_items", totalItems);
//        response.put("content", pageContent);
//
//        return response;
//    }
//}