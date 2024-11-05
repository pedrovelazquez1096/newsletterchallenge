package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.models.Metric;
import com.pvelazquez.newslettlerchallenge.services.MetricService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    private final MetricService metricService;

    @GetMapping
    public Metric getMetrics(){
        return metricService.getMetrics();
    }
}
