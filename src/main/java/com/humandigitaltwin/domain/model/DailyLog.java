package com.humandigitaltwin.domain.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_logs")
public class DailyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private LocalDate logDate;

    private double sleepHours;
    private double workHours;
    private int stressLevel; // 1-10
    private String activityLevel; // LOW, MEDIUM, HIGH

    // Calculated metrics that are saved
    private double calculatedEnergy;
    private double calculatedFocus;
    private double recoveryDebt;

    public DailyLog() {}

    public Long getId() { return id; }

    
    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }

    public double getSleepHours() { return sleepHours; }
    public void setSleepHours(double sleepHours) { this.sleepHours = sleepHours; }

    public double getWorkHours() { return workHours; }
    public void setWorkHours(double workHours) { this.workHours = workHours; }

    public int getStressLevel() { return stressLevel; }
    public void setStressLevel(int stressLevel) { this.stressLevel = stressLevel; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public double getCalculatedEnergy() { return calculatedEnergy; }
    public void setCalculatedEnergy(double calculatedEnergy) { this.calculatedEnergy = calculatedEnergy; }

    public double getCalculatedFocus() { return calculatedFocus; }
    public void setCalculatedFocus(double calculatedFocus) { this.calculatedFocus = calculatedFocus; }

    public double getRecoveryDebt() { return recoveryDebt; }
    public void setRecoveryDebt(double recoveryDebt) { this.recoveryDebt = recoveryDebt; }
}
