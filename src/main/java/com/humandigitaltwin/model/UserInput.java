package com.humandigitaltwin.model;

public class UserInput {
    private int sleepHours;
    private int workHours;
    private int stressLevel; // 1 to 5
    private ActivityLevel activityLevel;

    // Default constructor
    public UserInput() {
    }

    // Parameterized constructor
    public UserInput(int sleepHours, int workHours, int stressLevel, ActivityLevel activityLevel) {
        this.sleepHours = sleepHours;
        this.workHours = workHours;
        this.stressLevel = stressLevel;
        this.activityLevel = activityLevel;
    }

    // Getters and Setters
    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    @Override
    public String toString() {
        return "UserInput{" +
                "sleepHours=" + sleepHours +
                ", workHours=" + workHours +
                ", stressLevel=" + stressLevel +
                ", activityLevel=" + activityLevel +
                '}';
    }
}
