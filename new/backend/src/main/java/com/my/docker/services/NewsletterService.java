package com.my.docker.services;

import com.my.docker.models.Document;
import com.my.docker.models.Metric;
import com.my.docker.models.Newsletter;
import com.my.docker.models.Recipient;
import com.my.docker.models.dto.NewsletterDTO;
import com.my.docker.repositories.NewsletterRepo;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsletterService {
    private final RecipientService recipientService;
    private final EmailSenderService emailSenderService;
    private final DocumentService documentService;
    private final NewsletterRepo newsletterRepo;

    public void sendNewsletter(NewsletterDTO newsletterDTO) throws MessagingException {
        List<Recipient> recipients = recipientService.saveNewRecipientsAndGetSubcribedRecipients(newsletterDTO);
        List<Document> documents = documentService.getAllDocuments(newsletterDTO.getDocuments());


        emailSenderService.prepareEmail(newsletterDTO.getSubject(), recipients, documents);
    }

    public Metric getMetrics(){
        long mailsSent = newsletterRepo.count();
        int mailsScheduled = newsletterRepo.findAllByStatus(Newsletter.Status.SCHEDULED).size();
        long mailsCancelled = newsletterRepo.findAllByStatus(Newsletter.Status.CANCELLED).size();

        return Metric.builder()
                .mailsSent(mailsSent)
                .mailsScheduled(mailsScheduled)
                .mailsCancelled(mailsCancelled)
                .build();
    }

    public List<Newsletter> readScheduleNewsletter() {
        return newsletterRepo.findAllByStatus(Newsletter.Status.SCHEDULED);
    }
}
