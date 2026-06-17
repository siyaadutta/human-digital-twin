package com.humandigitaltwin.domain.model;

public record HumanState(
        double energy,
        double stress,
        double recoveryDebt,
        double focus) {
}
