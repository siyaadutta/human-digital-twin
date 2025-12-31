package com.humandigitaltwin;

import com.humandigitaltwin.model.*;
import com.humandigitaltwin.engine.DigitalTwinEngine;

public class Main {
    public static void main(String[] args) {

        UserInput input = new UserInput(
                6,
                9,
                4,
                ActivityLevel.MEDIUM);

        DigitalTwinEngine engine = new DigitalTwinEngine();
        TwinReport report = engine.run(input, 5);

        System.out.println(report.getRiskSummary());
        System.out.println(report.getInsights());
        System.out.println(report.getFutureTrajectory());
    }
}
