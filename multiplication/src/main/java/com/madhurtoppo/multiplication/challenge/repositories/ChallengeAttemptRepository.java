package com.madhurtoppo.multiplication.challenge.repositories;

import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeAttemptRepository extends JpaRepository<ChallengeAttempt, Long> {

    /**
     * @param userAlias
     * @return the last 10 attempts for a given user, identified by their alias
     */
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);

    /**
     * @param userAlias
     * @return the last attempts for a given user, identified by theri alias
     */
    @Query("SELECT a FROM ChallengeAttempt a WHERE a.user.alias = ?1 ORDER BY a.id DESC")
    List<ChallengeAttempt> lastAttempts(String userAlias);
}
