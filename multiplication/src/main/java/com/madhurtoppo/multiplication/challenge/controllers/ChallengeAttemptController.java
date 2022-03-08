package com.madhurtoppo.multiplication.challenge.controllers;

//import com.madhurtoppo.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.services.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    ResponseEntity<ChallengeAttempt> postResult(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping("/{alias}")
    ResponseEntity<List<ChallengeAttempt>> getStatistics(@PathVariable String alias) {
        return ResponseEntity.ok(challengeService.getStatsForUser(alias));
    }
}
