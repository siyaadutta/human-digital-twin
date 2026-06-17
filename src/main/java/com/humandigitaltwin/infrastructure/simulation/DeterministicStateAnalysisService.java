package com.humandigitaltwin.infrastructure.simulation;

import com.humandigitaltwin.domain.model.ActivityLevel;
import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationCommand;
import com.humandigitaltwin.domain.service.StateAnalysisService;
import org.springframework.stereotype.Service;

@Service
public class DeterministicStateAnalysisService implements StateAnalysisService {

    @Override
    public HumanState analyze(SimulationCommand command) {
        double energy = 100.0;
        energy -= command.workHours() * 2.0;
        energy -= command.stressLevel() * 4.5;

        double recoveryDebt = 0.0;
        if (command.sleepHours() < 7.0) {
            recoveryDebt = (7.0 - command.sleepHours()) * 12.0;
            energy -= recoveryDebt * 0.35;
        } else {
            energy += Math.min((command.sleepHours() - 7.0) * 4.0, 8.0);
        }

        double stress = (command.workHours() * 2.8) + (command.stressLevel() * 7.0) + (recoveryDebt * 0.2);
        double focus = 100.0 - Math.max(0.0, stress - 45.0) * 1.2 - recoveryDebt * 0.3;

        ActivityLevel activityLevel = command.activityLevel() == null ? ActivityLevel.MEDIUM : command.activityLevel();
        switch (activityLevel) {
            case LOW -> {
                energy -= 4.0;
                stress += 3.0;
            }
            case MEDIUM -> {
                energy += 2.0;
                focus += 2.0;
            }
            case HIGH -> {
                energy += 5.0;
                stress -= 4.0;
                focus += 4.0;
            }
        }

        return new HumanState(
                clamp(energy, 0, 100),
                clamp(stress, 0, 100),
                clamp(recoveryDebt, 0, 100),
                clamp(focus, 0, 100));
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
