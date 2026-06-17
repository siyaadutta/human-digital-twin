package com.humandigitaltwin.api.dto;

public record TrajectoryDayResponse(
        int day,
        double energy,
        double stress,
        double burnoutRisk) {
}
