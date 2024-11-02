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


    @GetMapping("unsubscribe")
    public String unsubscribeRecipient(@RequestParam("id") UUID uuid) throws NotFoundException {
        recipientService.unsubscribeRecipient(uuid);
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Unsubscribed</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "            align-items: center;\n" +
                "            height: 100vh;\n" +
                "            margin: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            text-align: center;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 30px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "            max-width: 400px;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #dc3545;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #333;\n" +
                "            margin: 20px 0;\n" +
                "        }\n" +
                "        a {\n" +
                "            display: inline-block;\n" +
                "            margin-top: 15px;\n" +
                "            padding: 10px 20px;\n" +
                "            color: white;\n" +
                "            background-color: #007bff;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "        a:hover {\n" +
                "            background-color: #0056b3;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Unsubscribed</h1>\n" +
                "        <p>You are no longer subscribed to our newsletter.</p>\n" +
                "        <p>We're sorry to see you go!</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @GetMapping
    public List<Recipient> getAllRecipients(){
        return recipientService.getAllRecipients();
    }
}
