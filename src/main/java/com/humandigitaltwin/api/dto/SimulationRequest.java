package com.humandigitaltwin.api.dto;

import com.humandigitaltwin.domain.model.ActivityLevel;
import com.humandigitaltwin.domain.model.SimulationCommand;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SimulationRequest(
        @DecimalMin(value = "0.0") @DecimalMax(value = "24.0") double sleepHours,
        @DecimalMin(value = "0.0") @DecimalMax(value = "24.0") double workHours,
        @Min(1) @Max(10) int stressLevel,
        @NotNull ActivityLevel activityLevel) {

    public SimulationCommand toCommand() {
        return new SimulationCommand(sleepHours, workHours, stressLevel, activityLevel);
    }
}
