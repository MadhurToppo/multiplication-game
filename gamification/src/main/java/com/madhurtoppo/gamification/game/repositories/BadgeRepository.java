package com.madhurtoppo.gamification.game.repositories;

import com.madhurtoppo.gamification.game.domain.BadgeCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<BadgeCard, Long> {

    /**
     * Retrieves all BadgeCards for a given user
     *
     * @param userId the id of user to look for badges
     * @return the list of BadgeCards, ordered by most recent first
     */
    List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(Long userId);
}
