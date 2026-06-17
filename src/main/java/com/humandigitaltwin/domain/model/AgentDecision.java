package com.humandigitaltwin.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AgentDecision(
        String decision,
        double confidence,
        List<String> reasoning) {
}
