package com.test.matching.controller;

import com.test.matching.entity.Matching;
import com.test.matching.entity.MatchingStatus;
import com.test.matching.service.MatchingService;
import com.test.matching.dto.MatchingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/matchings")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMatching(@RequestBody MatchingRequest request) {
        Matching matching = matchingService.createMatching(
                request.getPostId(),
                request.getCommentId(),
                request.getMatchingStatus()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("matchingId", matching.getMatchingId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> cancelMatching(@PathVariable("id") Long matchingId) {
        matchingService.cancelMatching(matchingId);

        // ✅ 매칭 취소 브로드캐스트
        simpMessagingTemplate.convertAndSend(
                "/topic/matching/" + matchingId,
                Map.of("type", "MATCH_CANCELLED", "matchingId", matchingId)
        );

        Map<String, Object> res = new HashMap<>();
        res.put("message", "매칭이 취소되었습니다.");
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateMatchingStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") MatchingStatus status) {

        matchingService.updateMatchingStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matching> getMatching(@PathVariable("id") Long matchingId) {
        Optional<Matching> matching = matchingService.getMatching(matchingId);
        return matching.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
