package com.madhurtoppo.multiplication.challenge.domain;

import com.madhurtoppo.multiplication.user.User;
import lombok.*;

import javax.persistence.*;

/**
 * This class identifies the attempt from a {@link User} to solve a challenge
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChallengeAttempt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    private Challenge challenge;

    private int resultAttempt;
    private boolean correct;
}
