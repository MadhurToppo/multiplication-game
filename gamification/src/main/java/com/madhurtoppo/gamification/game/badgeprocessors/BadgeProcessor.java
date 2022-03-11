package com.madhurtoppo.gamification.game.badgeprocessors;

import com.madhurtoppo.gamification.game.domain.BadgeType;
import com.madhurtoppo.gamification.game.domain.ChallengeSolvedEvent;
import com.madhurtoppo.gamification.game.domain.ScoreCard;

import java.util.List;
import java.util.Optional;

public interface BadgeProcessor {

    Optional<BadgeType> processForOptionalBadge(int currentScore, List<ScoreCard> scoreCards,
                                                ChallengeSolvedEvent solvedDTO);

    BadgeType badgeType();
}
