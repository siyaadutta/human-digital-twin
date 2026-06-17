package com.humandigitaltwin.infrastructure.simulation;

import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import com.humandigitaltwin.domain.service.BehaviorSimulationService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeterministicBehaviorSimulationService implements BehaviorSimulationService {

    @Override
    public List<SimulationDayResult> simulate(HumanState initialState, int days) {
        List<SimulationDayResult> results = new ArrayList<>();

        double energy = initialState.energy();
        double stress = initialState.stress();
        double recoveryDebt = initialState.recoveryDebt();
        double burnoutRisk = Math.max(0.0, (stress - energy) / 100.0);

        for (int day = 1; day <= days; day++) {
            if (recoveryDebt > 0) {
                energy -= 1.8;
                stress += 1.4;
                recoveryDebt -= 1.5;
            } else {
                energy += 0.7;
                stress -= 0.5;
            }

            if (energy < 60) {
                stress += 2.5;
            }

            burnoutRisk += (stress / 100.0) * 0.04;
            burnoutRisk -= (energy / 100.0) * 0.02;

            energy = clamp(energy, 0, 100);
            stress = clamp(stress, 0, 100);
            recoveryDebt = clamp(recoveryDebt, 0, 100);
            burnoutRisk = clamp(burnoutRisk, 0.0, 1.0);

            results.add(new SimulationDayResult(day, energy, stress, burnoutRisk));
        }

        return results;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
