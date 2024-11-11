package com.my.docker.controllers;

import com.my.docker.models.Metric;
import com.my.docker.services.MetricService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/metrics")
@CrossOrigin(origins = "*")
public class MetricsController {

    private final MetricService metricService;

    @GetMapping
    public Metric getMetrics(){
        return metricService.getMetrics();
    }
}
