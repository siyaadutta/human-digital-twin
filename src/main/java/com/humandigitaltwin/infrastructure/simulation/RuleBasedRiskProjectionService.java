package com.humandigitaltwin.infrastructure.simulation;

import com.humandigitaltwin.domain.model.RiskAssessment;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import com.humandigitaltwin.domain.service.RiskProjectionService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RuleBasedRiskProjectionService implements RiskProjectionService {

    @Override
    public RiskAssessment assess(List<SimulationDayResult> days) {
        if (days.isEmpty()) {
            return new RiskAssessment("No Data", "Insufficient simulation data.");
        }

        int highRiskDays = 0;
        boolean increasingRisk = true;
        boolean decliningEnergy = true;

        for (int i = 0; i < days.size(); i++) {
            SimulationDayResult day = days.get(i);
            if (day.burnoutRisk() >= 0.65 || (day.stress() > 70 && day.energy() < 45)) {
                highRiskDays++;
            }

            if (i > 0) {
                if (day.burnoutRisk() < days.get(i - 1).burnoutRisk()) {
                    increasingRisk = false;
                }
                if (day.energy() > days.get(i - 1).energy()) {
                    decliningEnergy = false;
                }
            }
        }

        if (highRiskDays >= 3) {
            return new RiskAssessment(
                    "High Burnout Risk",
                    "Three or more simulated days trend toward burnout and require workload reduction or recovery time.");
        }

        if (increasingRisk && decliningEnergy) {
            return new RiskAssessment(
                    "Compounding Fatigue",
                    "Burnout risk rises as energy falls across the trajectory, suggesting the current routine is unsustainable.");
        }

        if (increasingRisk) {
            return new RiskAssessment(
                    "Emerging Risk Trend",
                    "Burnout risk increases over time even though the current state is still recoverable.");
        }

        return new RiskAssessment(
                "Stable State",
                "No critical system-level pattern was detected in the five-day simulation.");
    }
}
