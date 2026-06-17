package com.humandigitaltwin.domain.service;

import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationCommand;

public interface StateAnalysisService {
    HumanState analyze(SimulationCommand command);
}
