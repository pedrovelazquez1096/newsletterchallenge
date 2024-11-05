package com.pvelazquez.newslettlerchallenge.services;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Metric;
import com.pvelazquez.newslettlerchallenge.models.Newsletter;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.repositories.NewsletterRepo;
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


        emailSenderService.prepareEmail("test", recipients, documents);
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
}
