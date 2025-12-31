package com.humandigitaltwin.model;

import java.util.List;

public class TwinReport {
    private HumanState currentState;
    private List<SimulationDayResult> futureTrajectory;
    private String riskSummary;
    private String insights;

    public TwinReport(HumanState currentState, List<SimulationDayResult> futureTrajectory, String riskSummary,
            String insights) {
        this.currentState = currentState;
        this.futureTrajectory = futureTrajectory;
        this.riskSummary = riskSummary;
        this.insights = insights;
    }

    public HumanState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(HumanState currentState) {
        this.currentState = currentState;
    }

    public List<SimulationDayResult> getFutureTrajectory() {
        return futureTrajectory;
    }

    public void setFutureTrajectory(List<SimulationDayResult> futureTrajectory) {
        this.futureTrajectory = futureTrajectory;
    }

    public String getRiskSummary() {
        return riskSummary;
    }

    public void setRiskSummary(String riskSummary) {
        this.riskSummary = riskSummary;
    }

    public String getInsights() {
        return insights;
    }

    public void setInsights(String insights) {
        this.insights = insights;
    }

    @Override
    public String toString() {
        return "TwinReport{" +
                "currentState=" + currentState +
                ", futureTrajectory=" + futureTrajectory +
                ", riskSummary='" + riskSummary + '\'' +
                ", insights='" + insights + '\'' +
                '}';
    }
}
