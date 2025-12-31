package com.humandigitaltwin.controller;

import com.humandigitaltwin.engine.DigitalTwinEngine;
import com.humandigitaltwin.model.TwinReport;
import com.humandigitaltwin.model.UserInput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DigitalTwinController {

    @PostMapping("/simulate")
    public TwinReport simulate(@RequestBody UserInput userInput) {
        DigitalTwinEngine engine = new DigitalTwinEngine();
        // Simulation days defaulting to 5 as per Main.java example
        return engine.run(userInput, 5);
    }
}
