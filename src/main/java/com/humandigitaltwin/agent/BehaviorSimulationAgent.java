package com.humandigitaltwin.agent;

import com.humandigitaltwin.model.HumanState;
import com.humandigitaltwin.model.SimulationDayResult;
import java.util.ArrayList;
import java.util.List;

public class BehaviorSimulationAgent {

    public List<SimulationDayResult> simulate(HumanState initialState, int days) {
        List<SimulationDayResult> results = new ArrayList<>();

        double energy = initialState.getEnergy();
        double stress = initialState.getStress();
        double recoveryDebt = initialState.getRecoveryDebt();
        double burnoutRisk = 0.0;

        for (int i = 1; i <= days; i++) {
            // Energy decreases slightly if recoveryDebt > 0
            if (recoveryDebt > 0) {
                energy -= 2.0;
            }

            // Stress increases slightly if energy < 60
            if (energy < 60) {
                stress += 3.0;
            }

            // Burnout risk increases when stress > 60 and energy < 50
            if (stress > 60 && energy < 50) {
                burnoutRisk += 0.05;
            }

            // RecoveryDebt slowly decreases if energy > 70
            if (energy > 70) {
                recoveryDebt -= 2.0;
            }

            // Clamp values
            energy = clamp(energy, 0, 100);
            stress = clamp(stress, 0, 100);
            recoveryDebt = clamp(recoveryDebt, 0, 100);
            burnoutRisk = clamp(burnoutRisk, 0.0, 1.0);

            results.add(new SimulationDayResult(i, energy, stress, burnoutRisk));
        }

        return results;
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}
