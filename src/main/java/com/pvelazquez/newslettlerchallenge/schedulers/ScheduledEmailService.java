package com.pvelazquez.newslettlerchallenge.schedulers;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Newsletter;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.repositories.NewsletterRepo;
import com.pvelazquez.newslettlerchallenge.services.EmailSenderService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ScheduledEmailService {
    private final NewsletterRepo newsletterRepo;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ScheduledEmailService(NewsletterRepo newsletterRepo, EmailSenderService emailSenderService) {
        this.newsletterRepo = newsletterRepo;
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
        this.emailSenderService = emailSenderService;
    }

    public UUID scheduleEmail(List<Recipient> recipients, List<Document> documents, Date scheduledTime, String subject) {
        try {
            Newsletter scheduledEmail = new Newsletter();
            scheduledEmail.setRecipients(recipients);
            scheduledEmail.setDocuments(documents);
            scheduledEmail.setScheduledTime(scheduledTime);
            scheduledEmail.setSubject(subject);
            scheduledEmail.setStatus(Newsletter.Status.SCHEDULED);

            scheduledEmail = newsletterRepo.save(scheduledEmail);

            Newsletter finalScheduledEmail = scheduledEmail;
            taskScheduler.schedule(() -> sendScheduledEmail(finalScheduledEmail.getId()), scheduledTime);

            return scheduledEmail.getId();
        }catch (Exception e){
            return null;
        }
    }

    public boolean cancelScheduledEmail(UUID emailId) {
        Newsletter scheduledEmail = newsletterRepo.findById(emailId).orElse(null);

        if (scheduledEmail != null && scheduledEmail.getStatus() == Newsletter.Status.SCHEDULED) {
            scheduledEmail.setStatus(Newsletter.Status.CANCELLED);
            newsletterRepo.save(scheduledEmail);
            log.info("Emails cancelled {}", scheduledEmail.getSubject());
            return true;
        }
        return false;
    }

    private void sendScheduledEmail(UUID emailId) {
        Newsletter scheduledEmail = newsletterRepo.findById(emailId).orElse(null);

        if (scheduledEmail != null && scheduledEmail.getStatus() == Newsletter.Status.SCHEDULED) {
            try {

                emailSenderService.prepareEmail(scheduledEmail.getSubject(), scheduledEmail.getRecipients(), scheduledEmail.getDocuments());

                scheduledEmail.setStatus(Newsletter.Status.SENT);

                newsletterRepo.save(scheduledEmail);
                log.info("Emails send to {}", scheduledEmail.getSubject());
            } catch (MessagingException e) {
                log.error(e.getMessage());
            }
        }
    }
}