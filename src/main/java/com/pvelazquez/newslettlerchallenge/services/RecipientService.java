package com.pvelazquez.newslettlerchallenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pvelazquez.newslettlerchallenge.exceptions.NotFoundException;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.repositories.RecipientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientService {
    private final RecipientRepo recipientRepo;
    private final PatcherUtil patcherUtil;

    public RecipientService(@Autowired RecipientRepo recipientRepo, @Autowired PatcherUtil patcherUtil){
        this.recipientRepo = recipientRepo;
        this.patcherUtil = patcherUtil;
    }

    public Recipient saveRecipient(Recipient recipient){
        return recipientRepo.save(recipient);
    }

    public void saveRecipients(List<String> emailList){
        try {
            for(String email : emailList) {
                recipientRepo.save(Recipient.builder()
                                .email(email)
                                .subscribed(true)
                                .build());
            }
        }catch (Exception ignored){

        }
    }

    public Recipient patchRecipient(UUID id, JsonPatch patch) throws JsonPatchException, JsonProcessingException, NotFoundException {
        Optional<Recipient> recipientOptional = recipientRepo.findById(id);

        if(recipientOptional.isEmpty())
            throw new NotFoundException("Recipient not found");

        Recipient recipient = recipientOptional.get();

        recipient = patcherUtil.applyPatchRecipient(patch, recipient);
        return recipientRepo.save(recipient);
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

    public Recipient unsubscribeRecipient(UUID uuid) throws NotFoundException {
        Recipient recipient = getRecipientById(uuid);

        recipient.setSubscribed(false);

        return recipientRepo.save(recipient);
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepo.findAll();
    }
}
