package com.humandigitaltwin.infrastructure.ai;

import com.humandigitaltwin.domain.model.AgentDecision;
import com.humandigitaltwin.domain.model.AiCoachMode;
import com.humandigitaltwin.domain.model.AiCoachResult;
import com.humandigitaltwin.domain.model.HumanState;
import com.humandigitaltwin.domain.model.SimulationDayResult;
import com.humandigitaltwin.domain.service.AiCoachService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringAiCoachService implements AiCoachService {

    private final ChatClient chatClient;
    private final AgentResponseParser parser;

    @Value("${ai.coach.system-prompt}")
    private String systemPrompt;

    public SpringAiCoachService(ChatClient.Builder chatClientBuilder, AgentResponseParser parser) {
        this.chatClient = chatClientBuilder.build();
        this.parser = parser;
    }

    @Override
    public AiCoachResult decide(HumanState currentState, List<SimulationDayResult> trajectory) {
        try {
            String prompt = buildPrompt(currentState, trajectory);
            
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            AgentDecision decision = parser.parse(response);
            
            return new AiCoachResult(
                "Groq AI Coach",
                AiCoachMode.LIVE,
                "llama-3.1-8b-instant",
                decision.decision(),
                decision.confidence(),
                decision.reasoning(),
                null
            );
            
        } catch (Exception e) {
            return new AiCoachResult(
                "Groq AI Coach",
                AiCoachMode.FALLBACK,
                "llama-3.1-8b-instant",
                "API failed",
                0.0,
                List.of("API failed: " + e.getMessage()),
                e.getMessage()
            );
        }
    }

    private String buildPrompt(HumanState currentState, List<SimulationDayResult> trajectory) {
        StringBuilder sb = new StringBuilder();
        sb.append(systemPrompt).append("\n");
        sb.append(String.format("NOW: energy:%.1f|stress:%.1f|focus:%.1f|debt:%.1f\n",
                currentState.energy(), currentState.stress(), currentState.focus(), currentState.recoveryDebt()));
        
        sb.append("TRAJ:");
        for (SimulationDayResult t : trajectory) {
            sb.append(String.format("[%d:e:%.0f|s:%.0f|r:%.1f]",
                    t.day(), t.energy(), t.stress(), t.burnoutRisk()));
        }
        
        sb.append("\nOUTPUT_FORMAT:{\"decision\":\"str\",\"confidence\":float,\"reasoning\":[\"str\"]}");
        return sb.toString();
    }
}
