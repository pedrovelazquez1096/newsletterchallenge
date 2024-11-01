package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.exceptions.NotFoundException;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import com.pvelazquez.newslettlerchallenge.services.RecipientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/recipient")
public class RecipientController {
    private final RecipientService recipientService;


    @PutMapping("unsubscribe")
    public Recipient unsubscribeRecipient(@RequestParam("id") UUID uuid) throws NotFoundException {
        return  recipientService.unsubscribeRecipient(uuid);
    }

    @GetMapping
    public List<Recipient> getAllRecipients(){
        return recipientService.getAllRecipients();
    }
}
