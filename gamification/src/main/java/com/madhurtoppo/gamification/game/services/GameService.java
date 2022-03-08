package com.madhurtoppo.gamification.game.services;

import com.madhurtoppo.gamification.game.domain.BadgeType;
import com.madhurtoppo.gamification.game.domain.ChallengeSolvedDTO;
import lombok.Value;

import java.util.List;

public interface GameService {

    /**
     * Process a new attempt from a given user
     *
     * @param challenge the challenge data with user details, factors, etc.
     * @return a {@link GameResult} object containing score and badges
     */
    GameResult newAttemptForUser(ChallengeSolvedDTO challenge);

    @Value
    class GameResult {
        int score;
        List<BadgeType> badges;
    }
}
