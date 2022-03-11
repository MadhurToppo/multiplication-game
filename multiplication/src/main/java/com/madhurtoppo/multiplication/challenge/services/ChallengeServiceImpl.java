package com.madhurtoppo.multiplication.challenge.services;

//import com.madhurtoppo.multiplication.challenge.dto.ChallengeAttemptDTO;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttemptDTO;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeAttemptRepository;
import com.madhurtoppo.multiplication.challenge.repositories.ChallengeRepository;
import com.madhurtoppo.multiplication.serviceclients.ChallengeEventPub;
//import com.madhurtoppo.multiplication.serviceclients.GamificationServiceClient;
import com.madhurtoppo.multiplication.user.User;
import com.madhurtoppo.multiplication.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeServiceImpl implements ChallengeService {

    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeAttemptRepository attemptRepository;
//    private final GamificationServiceClient gamificationServiceClient;
    private final ChallengeEventPub challengeEventPub;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        User user = userRepository.findByAlias(attemptDTO.getUserAlias()).orElseGet(() -> {
            log.info("Creating new user with alias {}", attemptDTO.getUserAlias());
            return userRepository.save(new User(attemptDTO.getUserAlias()));
        });
        Challenge challenge = challengeRepository.save(new Challenge(attemptDTO.getFactorA(), attemptDTO.getFactorB()));
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();

        ChallengeAttempt attempt = new ChallengeAttempt(null, user, challenge, attemptDTO.getGuess(), isCorrect);
        ChallengeAttempt storedAttempt = attemptRepository.save(attempt);
//        gamificationServiceClient.sendAttempt(storedAttempt);
        challengeEventPub.challengeSolved(storedAttempt, challenge);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
