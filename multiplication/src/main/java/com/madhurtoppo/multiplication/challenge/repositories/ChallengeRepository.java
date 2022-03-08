package com.madhurtoppo.multiplication.challenge.repositories;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
