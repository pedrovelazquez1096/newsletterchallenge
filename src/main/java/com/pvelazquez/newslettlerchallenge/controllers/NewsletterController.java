package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.schedulers.ScheduledEmailService;
import com.pvelazquez.newslettlerchallenge.services.DocumentService;
import com.pvelazquez.newslettlerchallenge.services.NewsletterService;
import com.pvelazquez.newslettlerchallenge.services.RecipientService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;
    private final ScheduledEmailService scheduledEmailService;
    private final RecipientService recipientService;
    private final DocumentService documentService;

    @PostMapping
    public void sendNewsletter(@RequestBody NewsletterDTO newsletterDTO) throws MessagingException {
        newsletterService.sendNewsletter(newsletterDTO);
    }

    @PostMapping("/schedule")
    public UUID scheduleNewsletter(@RequestBody NewsletterDTO newsletterDTO){
        List<Recipient> recipients = recipientService.saveNewRecipientsAndGetSubcribedRecipients(newsletterDTO);
        List<Document> documents = documentService.getAllDocuments(newsletterDTO.getDocuments());


        Date date = Date.from(newsletterDTO.getScheduleTime().atZone(ZoneId.systemDefault()).toInstant());
        return scheduledEmailService.scheduleEmail(recipients, documents, date, newsletterDTO.getSubject());
    }

    @PostMapping("/schedule/cancel")
    public boolean cancelScheduleEmail(@RequestParam UUID id){
        return scheduledEmailService.cancelScheduledEmail(id);
    }
}
