package StableMatch.example.StableMatch.controller;

import StableMatch.example.StableMatch.dto.MatchingRequest;
import StableMatch.example.StableMatch.dto.MatchingResponse;
import StableMatch.example.StableMatch.service.StableMatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    @Autowired
    private StableMatchingService stableMatchingService;

    @PostMapping("/solution")
    public ResponseEntity<MatchingResponse> solve(@RequestBody MatchingRequest request) {
        MatchingResponse response = stableMatchingService.solve(request);
        return ResponseEntity.ok(response);
    }
}
