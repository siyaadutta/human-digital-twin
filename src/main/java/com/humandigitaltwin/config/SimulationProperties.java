package com.humandigitaltwin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "simulation")
public record SimulationProperties(int days) {
}
