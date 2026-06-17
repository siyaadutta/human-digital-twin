package com.humandigitaltwin.api.mapper;

import com.humandigitaltwin.api.dto.AiCoachResponse;
import com.humandigitaltwin.api.dto.CurrentStateResponse;
import com.humandigitaltwin.api.dto.SimulationResponse;
import com.humandigitaltwin.api.dto.TrajectoryDayResponse;
import com.humandigitaltwin.domain.model.TwinReport;
import org.springframework.stereotype.Component;

@Component
public class SimulationResponseMapper {

    public SimulationResponse toResponse(TwinReport report) {
        return new SimulationResponse(
                new CurrentStateResponse(
                        report.currentState().energy(),
                        report.currentState().stress(),
                        report.currentState().recoveryDebt(),
                        report.currentState().focus()),
                report.futureTrajectory().stream()
                        .map(day -> new TrajectoryDayResponse(
                                day.day(),
                                day.energy(),
                                day.stress(),
                                day.burnoutRisk()))
                        .toList(),
                report.riskAssessment().summary(),
                report.riskAssessment().insights(),
                new AiCoachResponse(
                        report.aiCoachResult().agentName(),
                        report.aiCoachResult().mode(),
                        report.aiCoachResult().model(),
                        report.aiCoachResult().decision(),
                        report.aiCoachResult().confidence(),
                        report.aiCoachResult().reasoning(),
                        report.aiCoachResult().fallbackReason()));
    }
}
