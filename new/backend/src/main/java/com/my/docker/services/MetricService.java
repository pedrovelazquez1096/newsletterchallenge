package com.my.docker.services;

import com.my.docker.models.Metric;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MetricService {
    private final RecipientService recipientService;
    private final NewsletterService newsletterService;

    public Metric getMetrics() {
        Metric metricNewsletter = newsletterService.getMetrics();
        Metric metricRecipient = recipientService.getMetrics();
        return new Metric(metricNewsletter, metricRecipient);}
}
