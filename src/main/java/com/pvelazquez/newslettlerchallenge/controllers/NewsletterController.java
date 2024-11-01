package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.services.NewsletterService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;

    @PostMapping
    public void sendNewsletter(@RequestBody NewsletterDTO newsletterDTO) throws MessagingException {
        newsletterService.sendNewsletter(newsletterDTO);
    }
}
