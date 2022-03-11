package com.madhurtoppo.multiplication.serviceclients;

import com.madhurtoppo.multiplication.challenge.domain.Challenge;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeAttempt;
import com.madhurtoppo.multiplication.challenge.domain.ChallengeSolvedEvent;
import com.madhurtoppo.multiplication.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChallengeEventPubTest {

    private ChallengeEventPub challengeEventPub;

    @Mock private AmqpTemplate amqpTemplate;

    @BeforeEach
    void setUp() {
        challengeEventPub = new ChallengeEventPub(amqpTemplate, "test.topic");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttempt(boolean correct) {
        // given
        ChallengeAttempt attempt = createTestAttempt(correct);
        Challenge challenge = new Challenge(30, 40);

        // when
        challengeEventPub.challengeSolved(attempt, challenge);

        // then
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(ChallengeSolvedEvent.class);
        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(),
                                            routingKeyCaptor.capture(),
                                            eventCaptor.capture());
        then(exchangeCaptor.getValue()).isEqualTo("test.topic");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (correct ? "correct" : "wrong"));
        then(eventCaptor.getValue()).isEqualTo(solvedEvent(correct));
    }

    private ChallengeAttempt createTestAttempt(boolean correct) {
        Challenge challenge = new Challenge(30, 40);
        User user = new User(10L, "john");
        return new ChallengeAttempt(1L, user, challenge, correct ? 1200 : 1300, correct);
    }

    private ChallengeSolvedEvent solvedEvent(boolean correct) {
        return new ChallengeSolvedEvent(1L, correct, 30, 40, 10L, "john");
    }
}