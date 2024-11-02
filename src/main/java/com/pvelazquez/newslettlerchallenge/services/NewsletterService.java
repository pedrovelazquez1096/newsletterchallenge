package com.pvelazquez.newslettlerchallenge.services;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
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

    public void sendNewsletter(NewsletterDTO newsletterDTO) throws MessagingException {
        recipientService.saveRecipients(newsletterDTO.getEmails());
        List<Recipient> recipients = recipientService.findRecipients(newsletterDTO.getEmails());

        recipients = recipients.stream()
                .filter(Recipient::isSubscribed)
                .toList();

        List<Document> documents = documentService.getAllDocuments(newsletterDTO.getDocuments());


        emailSenderService.prepareEmail("test", recipients, documents);
    }
}
