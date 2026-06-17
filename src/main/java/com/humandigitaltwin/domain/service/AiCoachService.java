package com.humandigitaltwin.domain.service;

import com.humandigitaltwin.domain.model.AiCoachResult;
import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import java.util.List;

public interface AiCoachService {
    AiCoachResult decide(HumanState currentState, List<SimulationDayResult> trajectory);
}
