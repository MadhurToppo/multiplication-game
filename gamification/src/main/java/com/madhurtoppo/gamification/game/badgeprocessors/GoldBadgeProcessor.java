package com.madhurtoppo.gamification.game.badgeprocessors;

import com.madhurtoppo.gamification.game.domain.BadgeType;
import com.madhurtoppo.gamification.game.domain.ChallengeSolvedEvent;
import com.madhurtoppo.gamification.game.domain.ScoreCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GoldBadgeProcessor implements BadgeProcessor {
    @Override
    public Optional<BadgeType> processForOptionalBadge(int currentScore,
                                                       List<ScoreCard> scoreCards,
                                                       ChallengeSolvedEvent solvedDTO) {
        return currentScore > 400 ? Optional.of(BadgeType.GOLD) : Optional.empty();
    }

    @Override
    public BadgeType badgeType() {
        return BadgeType.GOLD;
    }
}
