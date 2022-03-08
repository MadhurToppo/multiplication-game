package com.madhurtoppo.multiplication.challenge.services;

import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
//import com.madhurtoppo.multiplication.challenge.dto.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeAttemptRepository;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeRepository;
import com.madhurtoppo.multiplication.serviceclients.GamificationServiceClient;
import com.madhurtoppo.multiplication.user.User;
import com.madhurtoppo.multiplication.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Mock private GamificationServiceClient gamificationServiceClient;

    @BeforeEach
    void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository,
                                                    challengeRepository,
                                                    attemptRepository,
                                                    gamificationServiceClient);
        given(attemptRepository.save(any())).will(returnsFirstArg());
    }

    @Test
    void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Madhur Toppo", 3000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();

        verify(userRepository).save(new User("Madhur Toppo"));
        verify(attemptRepository).save(resultAttempt);
        verify(gamificationServiceClient).sendAttempt(resultAttempt);
    }

    @Test
    void checkWrongAttemptTest() {
        // given
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
        given(userRepository.findByAlias("john_doe")).willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
    }
}