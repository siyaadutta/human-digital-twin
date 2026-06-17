package com.humandigitaltwin.infrastructure.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humandigitaltwin.domain.model.AgentDecision;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgentResponseParserTest {

    private final AgentResponseParser parser = new AgentResponseParser(new ObjectMapper());

    @Test
    void parsesPlainJsonResponse() {
        String response = """
                {
                  "decision": "Take a break",
                  "confidence": 0.8,
                  "reasoning": ["Stress is rising", "Energy is falling"]
                }
                """;

        AgentDecision decision = parser.parse(response);

        assertEquals("Take a break", decision.decision());
        assertEquals(0.8, decision.confidence());
        assertEquals(2, decision.reasoning().size());
    }

    @Test
    void parsesFencedJson() {
        String response = """
                ```json
                {
                  "decision": "Reduce workload",
                  "confidence": 0.73,
                  "reasoning": ["Burnout risk is climbing", "Short-term recovery is needed"]
                }
                ```
                """;

        AgentDecision decision = parser.parse(response);

        assertEquals("Reduce workload", decision.decision());
        assertEquals(0.73, decision.confidence());
    }

    @Test
    void parsesProsePlusJson() {
        String response = """
                Here is the decision:
                {
                  "decision": "Protect sleep",
                  "confidence": 0.66,
                  "reasoning": ["Recovery debt is elevated", "Sleep is the fastest lever"]
                }
                """;

        AgentDecision decision = parser.parse(response);

        assertEquals("Protect sleep", decision.decision());
        assertEquals(2, decision.reasoning().size());
    }

    @Test
    void rejectsMalformedOutput() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> parser.parse("not valid json"));

        assertEquals("Could not find JSON object in AI output.", error.getMessage());
    }

    @Test
    void rejectsEmptyResponse() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null));
    }
}
