package com.humandigitaltwin.infrastructure.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.humandigitaltwin.domain.model.AgentDecision;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class AgentResponseParser {
    private static final Pattern CODE_FENCE_PATTERN = Pattern.compile("```(?:json)?\\s*(\\{.*?})\\s*```", Pattern.DOTALL);

    private final ObjectMapper objectMapper;

    public AgentResponseParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AgentDecision parse(String response) {
        if (response == null || response.isBlank()) {
            throw new IllegalArgumentException("AI model returned an empty response body.");
        }

        try {
            JsonNode root = objectMapper.readTree(response);

            if (root.has("decision")) {
                return objectMapper.treeToValue(root, AgentDecision.class);
            }
        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception ignored) {
            return parseDecisionPayload(response);
        }

        return parseDecisionPayload(response);
    }

    private AgentDecision parseDecisionPayload(String payload) {
        String normalized = payload.trim();

        Matcher matcher = CODE_FENCE_PATTERN.matcher(normalized);
        if (matcher.find()) {
            normalized = matcher.group(1);
        } else {
            int start = normalized.indexOf('{');
            int end = normalized.lastIndexOf('}');
            if (start >= 0 && end > start) {
                normalized = normalized.substring(start, end + 1);
            }
        }

        if (!normalized.startsWith("{") || !normalized.endsWith("}")) {
            throw new IllegalArgumentException("Could not find JSON object in AI output.");
        }

        try {
            AgentDecision decision = objectMapper.readValue(normalized, AgentDecision.class);
            if (decision.decision() == null || decision.decision().isBlank()) {
                throw new IllegalArgumentException("Decision text is missing.");
            }
            if (decision.reasoning() == null || decision.reasoning().isEmpty()) {
                throw new IllegalArgumentException("Reasoning list is missing.");
            }
            return decision;
        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to parse AI JSON: " + exception.getMessage(), exception);
        }
    }
}
