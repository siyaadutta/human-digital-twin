package com.humandigitaltwin.domain.service;

import com.humandigitaltwin.domain.model.RiskAssessment;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import java.util.List;

public interface RiskProjectionService {
    RiskAssessment assess(List<SimulationDayResult> days);
}
