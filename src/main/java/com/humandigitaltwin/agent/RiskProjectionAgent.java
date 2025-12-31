package com.humandigitaltwin.agent;

import com.humandigitaltwin.model.SimulationDayResult;
import java.util.List;

public class RiskProjectionAgent {

    public static class RiskAssessment {
        private String riskSummary;
        private String insights;

        public RiskAssessment(String riskSummary, String insights) {
            this.riskSummary = riskSummary;
            this.insights = insights;
        }

        public String getRiskSummary() {
            return riskSummary;
        }

        public String getInsights() {
            return insights;
        }

        @Override
        public String toString() {
            return "RiskAssessment{" +
                    "riskSummary='" + riskSummary + '\'' +
                    ", insights='" + insights + '\'' +
                    '}';
        }
    }

    public RiskAssessment assess(List<SimulationDayResult> days) {
        StringBuilder summary = new StringBuilder();
        StringBuilder insights = new StringBuilder();

        int highStressLowEnergyDays = 0;
        boolean consistentlyIncreasingRisk = true;
        boolean consistentlyDecreasingEnergy = true;

        if (days.isEmpty()) {
            return new RiskAssessment("No Data", "Insufficient simulation data.");
        }

        for (int i = 0; i < days.size(); i++) {
            SimulationDayResult day = days.get(i);

            // Check for high stress and low energy
            if (day.getStress() > 60 && day.getEnergy() < 55) {
                highStressLowEnergyDays++;
            }

            // Trend analysis (requires previous day)
            if (i > 0) {
                // Check if risk is strictly increasing
                if (day.getBurnoutRisk() <= days.get(i - 1).getBurnoutRisk()) {
                    consistentlyIncreasingRisk = false;
                }
                // Check if energy is strictly decreasing
                if (day.getEnergy() >= days.get(i - 1).getEnergy()) {
                    consistentlyDecreasingEnergy = false;
                }
            }
        }

        boolean issueDetected = false;

        if (highStressLowEnergyDays >= 2) {
            summary.append("High Burnout Risk Detected. ");
            insights.append("Multiple days with high stress and low energy. ");
            issueDetected = true;
        }

        if (consistentlyIncreasingRisk && days.size() > 1 && days.get(days.size() - 1).getBurnoutRisk() > 0) {
            summary.append("Compounding Risk Trend. ");
            insights.append("Burnout risk is steadily increasing. ");
            issueDetected = true;
        }

        if (consistentlyDecreasingEnergy && days.size() > 1) {
            summary.append("Cumulative Fatigue. ");
            insights.append("Energy levels are steadily declining. ");
            issueDetected = true;
        }

        if (!issueDetected) {
            summary.append("Stable State.");
            insights.append("No critical patterns detected.");
        }

        return new RiskAssessment(summary.toString().trim(), insights.toString().trim());
    }
}
