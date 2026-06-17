package com.humandigitaltwin.domain.service;

import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import java.util.List;

public interface BehaviorSimulationService {
    List<SimulationDayResult> simulate(HumanState initialState, int days);
}
