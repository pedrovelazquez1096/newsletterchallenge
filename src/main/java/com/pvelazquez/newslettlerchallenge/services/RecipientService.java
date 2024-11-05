package com.pvelazquez.newslettlerchallenge.services;

import com.pvelazquez.newslettlerchallenge.exceptions.NotFoundException;
import com.pvelazquez.newslettlerchallenge.models.Metric;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.repositories.RecipientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RecipientService {
    private final RecipientRepo recipientRepo;

    public Metric getMetrics(){
        long subscribed = recipientRepo.findAllBySubscribed(true).size();
        long unSubscribed = recipientRepo.findAllBySubscribed(false).size();

        return Metric.builder()
                .recipientsUnsubscribed(unSubscribed)
                .recipientsSubscribed(subscribed)
                .totalRecipients(unSubscribed + subscribed)
                .build();
    }
    public void saveRecipients(List<String> emailList){
        for(String email : emailList) {
            try {
                recipientRepo.save(Recipient.builder()
                                .email(email)
                                .subscribed(true)
                                .build());
            }catch (Exception ignored){

            }
        }
    }
    public List<Recipient> saveNewRecipientsAndGetSubcribedRecipients(NewsletterDTO newsletterDTO){
        saveRecipients(newsletterDTO.getEmails());
        List<Recipient> recipients = findRecipients(newsletterDTO.getEmails());

        recipients = recipients.stream()
                .filter(Recipient::isSubscribed)
                .toList();

        return recipients;
    }
    public Recipient getRecipientById(UUID id) throws NotFoundException {
        Optional<Recipient> recipientOptional = recipientRepo.findById(id);

        if(recipientOptional.isEmpty())
            throw new NotFoundException("Recipient not found");

        return recipientOptional.get();
    }

    public List<Recipient> findRecipients(List<String> keyword){
        return recipientRepo.findAllByEmailIn(keyword);
    }

    public void unsubscribeRecipient(UUID uuid) throws NotFoundException {
        Recipient recipient = getRecipientById(uuid);

        recipient.setSubscribed(false);

        recipientRepo.save(recipient);
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepo.findAll();
    }
}
