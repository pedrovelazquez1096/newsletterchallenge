package com.pvelazquez.newslettlerchallenge.services;

import org.springframework.stereotype.Service;

@Service
public class EmailBodyService {

    public String getEmailBody(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Newsletter</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: auto;\n" +
                "            background: white;\n" +
                "            border-radius: 5px;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #007bff;\n" +
                "            color: white;\n" +
                "            padding: 20px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            background-color: #f1f1f1;\n" +
                "            text-align: center;\n" +
                "            padding: 10px;\n" +
                "            font-size: 12px;\n" +
                "        }\n" +
                "        a {\n" +
                "            color: #007bff;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Monthly Newsletter</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <h2>Welcome to Our Newsletter!</h2>\n" +
                "            <p>Dear Subscriber,</p>\n" +
                "            <p>We are excited to share the latest updates, news, and events with you!</p>\n" +
                "            <h3>What's New:</h3>\n" +
                "            <ul>\n" +
                "                <li>Update 1: Description of update 1.</li>\n" +
                "                <li>Update 2: Description of update 2.</li>\n" +
                "                <li>Event: Details about an upcoming event.</li>\n" +
                "            </ul>\n" +
                "            <p>Thank you for being a part of our community!</p>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2024 Your Company. All rights reserved.</p>\n" +
                "            <p><a href=\"#\">Unsubscribe</a> | <a href=\"#\">Privacy Policy</a></p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
