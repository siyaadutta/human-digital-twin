package com.humandigitaltwin.api.dto;

import com.humandigitaltwin.domain.model.AiCoachMode;
import java.util.List;

public record AiCoachResponse(
        String agentName,
        AiCoachMode mode,
        String model,
        String decision,
        double confidence,
        List<String> reasoning,
        String fallbackReason) {
}
