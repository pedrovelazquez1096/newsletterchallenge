package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.models.dto.NewsletterDTO;
import com.pvelazquez.newslettlerchallenge.schedulers.EmailScheduler;
import com.pvelazquez.newslettlerchallenge.services.NewsletterService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;
    private final EmailScheduler emailScheduler;

    @PostMapping
    public void sendNewsletter(@RequestBody NewsletterDTO newsletterDTO) throws MessagingException {
        newsletterService.sendNewsletter(newsletterDTO);
    }

    @PostMapping("/schedule")
    public void scheduleNewsletter(@RequestParam int time){
        emailScheduler.scheduleTask(time);
    }
}
