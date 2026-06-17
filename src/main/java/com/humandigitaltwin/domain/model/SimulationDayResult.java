package com.humandigitaltwin.domain.model;

public record SimulationDayResult(
        int day,
        double energy,
        double stress,
        double burnoutRisk) {
}
