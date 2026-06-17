package com.humandigitaltwin.infrastructure.ai;

import com.humandigitaltwin.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpringAiCoachServiceTest {

    @Mock
    private ChatClient.Builder chatClientBuilder;

    @Mock
    private AgentResponseParser parser;

    private ChatClient chatClient;
    private SpringAiCoachService aiCoachService;

    @BeforeEach
    public void setUp() {
        chatClient = mock(ChatClient.class, Answers.RETURNS_DEEP_STUBS);
        when(chatClientBuilder.build()).thenReturn(chatClient);
        
        aiCoachService = new SpringAiCoachService(chatClientBuilder, parser);
        ReflectionTestUtils.setField(aiCoachService, "systemPrompt", "Test System Prompt");
    }

    @Test
    public void testDecide_Success() {
        HumanState state = new HumanState(80, 40, 10, 70);
        List<SimulationDayResult> traj = List.of(new SimulationDayResult(1, 75, 45, 0.1));
        
        String jsonResponse = "{\"decision\": \"Keep it up\", \"confidence\": 0.95, \"reasoning\": [\"Good sleep\"]}";
        AgentDecision mockDecision = new AgentDecision("Keep it up", 0.95, List.of("Good sleep"));

        when(chatClient.prompt().user(anyString()).call().content()).thenReturn(jsonResponse);
        when(parser.parse(jsonResponse)).thenReturn(mockDecision);

        AiCoachResult result = aiCoachService.decide(state, traj);

        assertNotNull(result);
        assertEquals(AiCoachMode.LIVE, result.mode());
        assertEquals("Keep it up", result.decision());
        assertEquals(0.95, result.confidence());
        assertNull(result.fallbackReason());
    }

    @Test
    public void testDecide_FallbackOnException() {
        HumanState state = new HumanState(80, 40, 10, 70);
        List<SimulationDayResult> traj = List.of();

        when(chatClient.prompt()).thenThrow(new RuntimeException("Network Error"));

        AiCoachResult result = aiCoachService.decide(state, traj);

        assertNotNull(result);
        assertEquals(AiCoachMode.FALLBACK, result.mode());
        assertEquals("API failed", result.decision());
        assertEquals(0.0, result.confidence());
        assertTrue(result.fallbackReason().contains("Network Error"));
    }
}
