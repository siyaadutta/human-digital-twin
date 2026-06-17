package com.humandigitaltwin.domain.model;

import java.util.List;

public record AiCoachResult(
        String agentName,
        AiCoachMode mode,
        String model,
        String decision,
        double confidence,
        List<String> reasoning,
        String fallbackReason) {
}
