package com.madhurtoppo.gamification.game.badgeprocessors;

import com.madhurtoppo.gamification.game.domain.BadgeType;
import com.madhurtoppo.gamification.game.domain.ChallengeSolvedDTO;
import com.madhurtoppo.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BadgeProcessorImpl implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards,
                                                       ChallengeSolvedDTO solvedDTO)
    {
        return currentScore > 50 ? Optional.of(BadgeType.BRONZE) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.BRONZE;
    }
}
