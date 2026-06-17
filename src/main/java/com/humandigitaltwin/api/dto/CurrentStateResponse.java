package com.humandigitaltwin.api.dto;

public record CurrentStateResponse(
        double energy,
        double stress,
        double recoveryDebt,
        double focus) {
}
