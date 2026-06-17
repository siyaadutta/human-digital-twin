package com.humandigitaltwin.infrastructure.simulation;

import com.humandigitaltwin.domain.model.ActivityLevel;
import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.RiskAssessment;
import com.humandigitaltwin.domain.model.SimulationCommand;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationServicesTest {

    private final DeterministicStateAnalysisService stateAnalysisService = new DeterministicStateAnalysisService();
    private final DeterministicBehaviorSimulationService behaviorSimulationService = new DeterministicBehaviorSimulationService();
    private final RuleBasedRiskProjectionService riskProjectionService = new RuleBasedRiskProjectionService();

    @Test
    void stateAnalysisProducesBoundedValues() {
        HumanState state = stateAnalysisService.analyze(new SimulationCommand(5.5, 10.0, 8, ActivityLevel.LOW));

        assertTrue(state.energy() >= 0 && state.energy() <= 100);
        assertTrue(state.stress() >= 0 && state.stress() <= 100);
        assertTrue(state.recoveryDebt() > 0);
    }

    @Test
    void behaviorSimulationReturnsRequestedDays() {
        List<SimulationDayResult> days = behaviorSimulationService.simulate(new HumanState(60, 55, 12, 70), 5);

        assertEquals(5, days.size());
        assertFalse(days.get(4).burnoutRisk() < 0);
    }

    @Test
    void riskProjectionDetectsStableState() {
        RiskAssessment assessment = riskProjectionService.assess(List.of(
                new SimulationDayResult(1, 80, 30, 0.10),
                new SimulationDayResult(2, 81, 29, 0.08),
                new SimulationDayResult(3, 82, 28, 0.07)));

        assertEquals("Stable State", assessment.summary());
    }
}
