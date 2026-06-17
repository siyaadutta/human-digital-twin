package com.humandigitaltwin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humandigitaltwin.api.dto.SimulationRequest;
import com.humandigitaltwin.api.dto.SimulationResponse;
import com.humandigitaltwin.api.dto.CurrentStateResponse;
import com.humandigitaltwin.api.mapper.SimulationResponseMapper;
import com.humandigitaltwin.application.DigitalTwinSimulationService;
import com.humandigitaltwin.domain.model.*;
import com.humandigitaltwin.domain.repository.DailyLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DailyLogRepository logRepository;

    @MockBean
    private DigitalTwinSimulationService simulationService;

    @MockBean
    private SimulationResponseMapper responseMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetHistory() throws Exception {
        DailyLog log = new DailyLog();
        log.setLogDate(LocalDate.now());
        log.setCalculatedEnergy(85.0);

        when(logRepository.findTop7ByOrderByLogDateDesc()).thenReturn(List.of(log));

        mockMvc.perform(get("/api/logs/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].calculatedEnergy").value(85.0));
    }

    @Test
    public void testClearLogs() throws Exception {
        mockMvc.perform(delete("/api/logs"))
                .andExpect(status().isOk());
        verify(logRepository).deleteAll();
    }

    @Test
    public void testSubmitLog_ValidRequest() throws Exception {
        SimulationRequest req = new SimulationRequest(8.0, 8.0, 5, ActivityLevel.MEDIUM);
        TwinReport report = new TwinReport(
            new HumanState(100, 50, 0, 80),
            List.of(),
            new RiskAssessment("Low Risk", "Keep it up"),
            new AiCoachResult("Coach", AiCoachMode.LIVE, "Model", "Advice", 0.9, List.of(), null)
        );
        SimulationResponse resp = new SimulationResponse(
            new CurrentStateResponse(100, 50, 0, 80),
            List.of(),
            "Low Risk",
            "Keep it up",
            null
        );

        when(simulationService.simulate(any(SimulationCommand.class))).thenReturn(report);
        when(responseMapper.toResponse(report)).thenReturn(resp);

        mockMvc.perform(post("/api/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentState.energy").value(100.0));
    }

    @Test
    public void testSubmitLog_InvalidSleep() throws Exception {
        SimulationRequest req = new SimulationRequest(-1.0, 8.0, 5, ActivityLevel.MEDIUM); // invalid sleep

        mockMvc.perform(post("/api/logs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
