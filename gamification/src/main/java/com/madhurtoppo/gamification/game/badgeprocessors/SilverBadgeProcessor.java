package com.madhurtoppo.gamification.game.badgeprocessors;

import com.madhurtoppo.gamification.game.domain.BadgeType;
import com.madhurtoppo.gamification.game.domain.ChallengeSolvedEvent;
import com.madhurtoppo.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SilverBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                       List<ScoreCard> scoreCards,
                                                       ChallengeSolvedEvent solvedDTO) {
        return currentScore > 150 ? Optional.of(BadgeType.SILVER) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.SILVER;
    }
}
