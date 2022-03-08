package com.madhurtoppo.multiplication.challenge.services;

//import com.madhurtoppo.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;

import java.util.List;

public interface ChallengeService {
    /**
     * Verifies if the attempt coming from the presentation layer is correct or not
     * @param attemptDTO
     * @return the resulting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO);

    /**
     * Gets the user's statistics
     * @param userAlias the user's alias
     * @return a list of last 10 {@link ChallengeAttempt}
     */
    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
