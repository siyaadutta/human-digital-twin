package com.humandigitaltwin.application;

import com.humandigitaltwin.config.SimulationProperties;
import com.humandigitaltwin.domain.model.AiCoachResult;
import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.RiskAssessment;
import com.humandigitaltwin.domain.model.SimulationCommand;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import com.humandigitaltwin.domain.model.TwinReport;
import com.humandigitaltwin.domain.service.AiCoachService;
import com.humandigitaltwin.domain.service.BehaviorSimulationService;
import com.humandigitaltwin.domain.service.RiskProjectionService;
import com.humandigitaltwin.domain.service.StateAnalysisService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DigitalTwinSimulationService {
    private final StateAnalysisService stateAnalysisService;
    private final BehaviorSimulationService behaviorSimulationService;
    private final RiskProjectionService riskProjectionService;
    private final AiCoachService aiCoachService;
    private final SimulationProperties simulationProperties;

    public DigitalTwinSimulationService(StateAnalysisService stateAnalysisService,
            BehaviorSimulationService behaviorSimulationService,
            RiskProjectionService riskProjectionService,
            AiCoachService aiCoachService,
            SimulationProperties simulationProperties) {
        this.stateAnalysisService = stateAnalysisService;
        this.behaviorSimulationService = behaviorSimulationService;
        this.riskProjectionService = riskProjectionService;
        this.aiCoachService = aiCoachService;
        this.simulationProperties = simulationProperties;
    }

    public TwinReport simulate(SimulationCommand command) {
        HumanState currentState = stateAnalysisService.analyze(command);
        List<SimulationDayResult> trajectory = behaviorSimulationService.simulate(
                currentState,
                simulationProperties.days());
        RiskAssessment riskAssessment = riskProjectionService.assess(trajectory);
        AiCoachResult aiCoachResult = aiCoachService.decide(currentState, trajectory);

        return new TwinReport(currentState, trajectory, riskAssessment, aiCoachResult);
    }
}
