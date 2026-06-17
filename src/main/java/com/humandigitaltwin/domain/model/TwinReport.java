package com.humandigitaltwin.domain.model;

import java.util.List;

public record TwinReport(
        HumanState currentState,
        List<SimulationDayResult> futureTrajectory,
        RiskAssessment riskAssessment,
        AiCoachResult aiCoachResult) {
}
