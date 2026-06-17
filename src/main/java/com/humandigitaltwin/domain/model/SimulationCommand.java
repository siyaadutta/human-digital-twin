package com.humandigitaltwin.domain.model;

public record SimulationCommand(
        double sleepHours,
        double workHours,
        int stressLevel,
        ActivityLevel activityLevel) {
}
