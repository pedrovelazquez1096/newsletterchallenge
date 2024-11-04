package com.pvelazquez.newslettlerchallenge.schedulers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class ScheduledEmailService {

    private final JavaMailSender mailSender;
    private final ThreadPoolTaskScheduler taskScheduler;

    public ScheduledEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    public void scheduleEmail(String to, String subject, String text, Date scheduledTime) {
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(() -> sendEmail(to, subject, text), scheduledTime);
        // You can store scheduledTask if you need to manage or cancel it later
    }

    private void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);

            mailSender.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}