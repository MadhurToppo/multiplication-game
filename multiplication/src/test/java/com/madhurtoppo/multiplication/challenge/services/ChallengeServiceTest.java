package com.madhurtoppo.multiplication.challenge.services;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeAttemptRepository;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeRepository;
import com.madhurtoppo.multiplication.serviceclients.ChallengeEventPub;
import com.madhurtoppo.multiplication.user.User;
import com.madhurtoppo.multiplication.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeServiceTest {

    private ChallengeService challengeService;

    @Mock private UserRepository userRepository;
    @Mock private ChallengeAttemptRepository attemptRepository;
    @Mock private ChallengeRepository challengeRepository;
    @Mock private ChallengeEventPub challengeEventPub;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository,
                                                    challengeRepository,
                                                    attemptRepository,
                                                    challengeEventPub);
    }

    @Test
    void checkCorrectAttemptTest() {
        // given
        given(attemptRepository.save(any())).will(returnsFirstArg());
        given(challengeRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Madhur Toppo", 3000);
        Challenge challenge = new Challenge(attemptDTO.getFactorA(), attemptDTO.getFactorB());

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();
        verify(userRepository).save(new User(attemptDTO.getUserAlias()));
        verify(attemptRepository).save(resultAttempt);
        verify(challengeEventPub).challengeSolved(resultAttempt, challenge);
    }

    @Test
    void checkWrongAttemptTest() {
        // given
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Mukul Toppo", 4000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();
    }

    @Test
    void checkExistingUserTest() {
        // given
        User existingUser = new User(1L, "john_doe");
        given(attemptRepository.save(any())).will(returnsFirstArg());
        given(userRepository.findByAlias(existingUser.getAlias())).willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, existingUser.getAlias(), 5000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void retrieveStatsTest() {
        // given
        User user = new User("john_doe");
        Challenge challenge1 = new Challenge(50, 60);
        Challenge challenge2 = new Challenge(50, 60);
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, challenge1, 3010, false);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, challenge2, 3051, false);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUserAliasOrderByIdDesc(user.getAlias())).willReturn(lastAttempts);

        // when
        List<ChallengeAttempt> latestAttemptsResult = challengeService.getStatsForUser(user.getAlias());

        // then
        then(latestAttemptsResult).isEqualTo(lastAttempts);
    }
}