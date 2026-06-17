package com.humandigitaltwin.api.dto;

import java.util.List;

public record SimulationResponse(
        CurrentStateResponse currentState,
        List<TrajectoryDayResponse> futureTrajectory,
        String riskSummary,
        String systemInsights,
        AiCoachResponse aiCoach) {
}
