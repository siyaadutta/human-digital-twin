package com.humandigitaltwin.api;

import com.humandigitaltwin.api.dto.SimulationRequest;
import com.humandigitaltwin.api.dto.SimulationResponse;
import com.humandigitaltwin.api.mapper.SimulationResponseMapper;
import com.humandigitaltwin.application.DigitalTwinSimulationService;
import com.humandigitaltwin.domain.model.DailyLog;
import com.humandigitaltwin.domain.repository.DailyLogRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final DailyLogRepository logRepository;
    private final DigitalTwinSimulationService simulationService;
    private final SimulationResponseMapper responseMapper;

    public LogController(DailyLogRepository logRepository, 
                         DigitalTwinSimulationService simulationService, SimulationResponseMapper responseMapper) {
        this.logRepository = logRepository;
        this.simulationService = simulationService;
        this.responseMapper = responseMapper;
    }

    @Operation(summary = "Submit a daily log and run AI twin simulation")
    @PostMapping
    public ResponseEntity<SimulationResponse> submitLog(@Valid @RequestBody SimulationRequest request) {
        DailyLog log = new DailyLog();
        log.setLogDate(LocalDate.now());
        log.setSleepHours(request.sleepHours());
        log.setWorkHours(request.workHours());
        log.setStressLevel(request.stressLevel());
        log.setActivityLevel(request.activityLevel().name());

        var twinReport = simulationService.simulate(request.toCommand());

        log.setCalculatedEnergy(twinReport.currentState().energy());
        log.setCalculatedFocus(twinReport.currentState().focus());
        log.setRecoveryDebt(twinReport.currentState().recoveryDebt());
        
        logRepository.save(log);

        return ResponseEntity.ok(responseMapper.toResponse(twinReport));
    }

    @Operation(summary = "Run AI twin simulation without saving (What-If Scenario)")
    @PostMapping("/simulate")
    public ResponseEntity<SimulationResponse> simulateOnly(@Valid @RequestBody SimulationRequest request) {
        var twinReport = simulationService.simulate(request.toCommand());
        return ResponseEntity.ok(responseMapper.toResponse(twinReport));
    }

    @Operation(summary = "Get historical logs")
    @GetMapping("/history")
    public ResponseEntity<List<DailyLog>> getHistory() {
        List<DailyLog> logs = logRepository.findTop7ByOrderByLogDateDesc();
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Clear all historical logs")
    @DeleteMapping
    public ResponseEntity<Void> clearLogs() {
        logRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}
