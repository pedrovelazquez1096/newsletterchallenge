package com.pvelazquez.newslettlerchallenge.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class EmailScheduler {
    private final TaskScheduler taskScheduler;
    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    @Autowired
    public EmailScheduler(TaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
        //scheduleTask(1);
    }

    public void scheduleTask(int time) {
        Runnable task = () -> logger.info("Programmatically scheduled task performed at {}", LocalDateTime.now());
        taskScheduler.scheduleWithFixedDelay(task, Duration.ofSeconds(time));
    }
}