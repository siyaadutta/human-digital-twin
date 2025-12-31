package com.humandigitaltwin.model;

public class HumanState {
    private double energy;
    private double stress;
    private double recoveryDebt;
    private double focus;

    // Default constructor
    public HumanState() {
    }

    // Parameterized constructor
    public HumanState(double energy, double stress, double recoveryDebt, double focus) {
        this.energy = energy;
        this.stress = stress;
        this.recoveryDebt = recoveryDebt;
        this.focus = focus;
    }

    // Getters and Setters
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

    public double getRecoveryDebt() {
        return recoveryDebt;
    }

    public void setRecoveryDebt(double recoveryDebt) {
        this.recoveryDebt = recoveryDebt;
    }

    public double getFocus() {
        return focus;
    }

    public void setFocus(double focus) {
        this.focus = focus;
    }

    public String toString() {
        return "HumanState{" +
                "energy=" + energy +
                ", stress=" + stress +
                ", recoveryDebt=" + recoveryDebt +
                ", focus=" + focus +
                '}';
    }
}
