package com.humandigitaltwin.agent;

import com.humandigitaltwin.model.HumanState;
import com.humandigitaltwin.model.UserInput;

public class StateAnalysisAgent {

    public HumanState analyze(UserInput input) {
        // Base energy
        double energy = 100.0;

        // Reduce energy
        energy -= (input.getWorkHours() * 2);
        energy -= (input.getStressLevel() * 5);

        // Recovery debt
        double recoveryDebt = 0.0;
        if (input.getSleepHours() < 7) {
            recoveryDebt = (7 - input.getSleepHours()) * 10.0;
        }

        // Stress increases with workHours and stressLevel
        double stress = (input.getWorkHours() * 2.5) + (input.getStressLevel() * 8);

        // Focus decreases when stress is high
        double focus = 100.0;
        if (stress > 50) {
            focus -= (stress - 50) * 1.5;
        }

        // Clamp values
        energy = clamp(energy, 0, 100);
        stress = clamp(stress, 0, 100);
        recoveryDebt = clamp(recoveryDebt, 0, 100);
        focus = clamp(focus, 0, 100);

        return new HumanState(energy, stress, recoveryDebt, focus);
    }

    private double clamp(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }
}
