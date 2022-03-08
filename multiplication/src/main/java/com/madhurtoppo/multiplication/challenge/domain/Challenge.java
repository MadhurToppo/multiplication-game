package com.madhurtoppo.multiplication.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class represents a challenge for a Multiplication between 2 numbers (factorA * factorB)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Challenge {

    @Id
    @GeneratedValue
    private Long id;

    private int factorA;
    private int factorB;

    public Challenge(final int factorA, final int factorB) {
        this(null, factorA, factorB);
    }
}
