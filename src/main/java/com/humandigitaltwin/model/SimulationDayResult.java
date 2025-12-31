package com.humandigitaltwin.model;

public class SimulationDayResult {
    private int day;
    private double energy;
    private double stress;
    private double burnoutRisk;

    public SimulationDayResult(int day, double energy, double stress, double burnoutRisk) {
        this.day = day;
        this.energy = energy;
        this.stress = stress;
        this.burnoutRisk = burnoutRisk;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getStress() {
        return stress;
    }

    public void setStress(double stress) {
        this.stress = stress;
    }

    public double getBurnoutRisk() {
        return burnoutRisk;
    }

    public void setBurnoutRisk(double burnoutRisk) {
        this.burnoutRisk = burnoutRisk;
    }

    public String toString() {
        return "SimulationDayResult{" +
                "day=" + day +
                ", energy=" + energy +
                ", stress=" + stress +
                ", burnoutRisk=" + burnoutRisk +
                '}';
    }
}
