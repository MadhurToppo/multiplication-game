package com.madhurtoppo.multiplication.serviceclients;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeSolvedEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final AmqpTemplate amqpTemplate;
    private final String challengesTopicExchange;

    public ChallengeEventPub(final AmqpTemplate amqpTemplate,
                             @Value("${amqp.exchange.attempts}") final String challengesTopicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.challengesTopicExchange = challengesTopicExchange;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt,
                                final Challenge challenge) {
        ChallengeSolvedEvent event = buildEvent(challengeAttempt, challenge);
        String routingKey = "attempt." + (event.isCorrect() ? "correct" : "wrong");
        amqpTemplate.convertAndSend(challengesTopicExchange, routingKey, event);
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt attempt,
                                            final Challenge challenge) {
        return new ChallengeSolvedEvent(attempt.getId(),
                                        attempt.isCorrect(),
                                        challenge.getFactorA(),
                                        challenge.getFactorB(),
                                        attempt.getUser().getId(),
                                        attempt.getUser().getAlias());
    }
}
