package com.humandigitaltwin.engine;

import com.humandigitaltwin.agent.BehaviorSimulationAgent;
import com.humandigitaltwin.agent.RiskProjectionAgent;
import com.humandigitaltwin.agent.StateAnalysisAgent;
import com.humandigitaltwin.model.HumanState;
import com.humandigitaltwin.model.SimulationDayResult;
import com.humandigitaltwin.model.TwinReport;
import com.humandigitaltwin.model.UserInput;
import java.util.List;

public class DigitalTwinEngine {

    private final StateAnalysisAgent stateAnalysisAgent;
    private final BehaviorSimulationAgent behaviorSimulationAgent;
    private final RiskProjectionAgent riskProjectionAgent;

    public DigitalTwinEngine() {
        this.stateAnalysisAgent = new StateAnalysisAgent();
        this.behaviorSimulationAgent = new BehaviorSimulationAgent();
        this.riskProjectionAgent = new RiskProjectionAgent();
    }

    public TwinReport run(UserInput input, int simulationDays) {
        // 1. Analyze Current State
        HumanState currentState = stateAnalysisAgent.analyze(input);

        // 2. Simulate Future
        List<SimulationDayResult> futureTrajectory = behaviorSimulationAgent.simulate(currentState, simulationDays);

        // 3. Project Risks
        RiskProjectionAgent.RiskAssessment assessment = riskProjectionAgent.assess(futureTrajectory);

        // 4. Construct Report
        return new TwinReport(
                currentState,
                futureTrajectory,
                assessment.getRiskSummary(),
                assessment.getInsights());
    }
}
