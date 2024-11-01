package com.pvelazquez.newslettlerchallenge.services;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.eclipse.angus.mail.iap.ByteArray;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final AzureBlobClient client;

    @Value("${notification.host_name}")
    String hostName;

    @Value("${notification.host_port}")
    String hostPort;

    public void sendEmail(String subject, String message, List<Recipient> recipientList, boolean html, List<Document> documents) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(hostName);
        mailSender.setPort(Integer.parseInt(hostPort));
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("newsletter@home.com");
        mimeMessageHelper.setText(message, html);

        String[] email = recipientList.stream()
                                .map(Recipient::getEmail)
                                .toArray(String[]::new);

        mimeMessageHelper.setTo(email);

        mimeMessageHelper.setSubject(subject);

        for(Document document : documents){
            String base64 = client.downloadFile(document.getUrl());

            ByteArrayResource byteArrayResource = new ByteArrayResource(Base64.getDecoder().decode(base64));

            mimeMessageHelper.addAttachment(document.getFileName(), byteArrayResource);
        }

        mailSender.send(mimeMessage);
    }
}
