package com.test.matching.controller;

import com.test.matching.entity.Matching;
import com.test.matching.service.MatchingService;
import com.test.matching.dto.MatchingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/matchings")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @PostMapping
    public ResponseEntity<Void> createMatching(@RequestBody MatchingRequest request) {
        matchingService.createMatching(
                request.getPostId(),
                request.getCommentId(),
                request.getMatchingStatus()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matching> getMatching(@PathVariable("id") Long matchingId) {
        Optional<Matching> matching = matchingService.getMatching(matchingId);
        return matching.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatching(@PathVariable("id") Long matchingId) {
        matchingService.deleteMatching(matchingId);
        return ResponseEntity.noContent().build();
    }
}
