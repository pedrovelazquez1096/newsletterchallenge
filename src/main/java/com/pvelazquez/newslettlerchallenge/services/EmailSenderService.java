package com.pvelazquez.newslettlerchallenge.services;

import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final AzureBlobClient client;
    private final EmailBodyService emailBodyService;

    @Value("${notification.host_name}")
    String hostName;

    @Value("${notification.host_port}")
    String hostPort;

    public void prepareEmail(String subject, List<Recipient> recipientList, List<Document> documents) throws MessagingException {
        HashMap<String, ByteArrayResource> documentsBytes = new HashMap<>();

        for(Document document : documents){
            String base64 = client.downloadFile(document.getUrl());

            ByteArrayResource byteArrayResource = new ByteArrayResource(Base64.getDecoder().decode(base64));

            documentsBytes.put(document.getFileName(), byteArrayResource);
        }

        for(Recipient recipient : recipientList)
            sendEmail(subject, recipient, documentsBytes);

    }

    private void sendEmail(String subject, Recipient recipient, HashMap<String, ByteArrayResource> documentsBytes) throws MessagingException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(hostName);
        mailSender.setPort(Integer.parseInt(hostPort));
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("newsletter@home.com");
        mimeMessageHelper.setTo(recipient.getEmail());

        mimeMessageHelper.setText(emailBodyService.emailBodyWithUnsubcribe("http://localhost:8080/api/v1/recipient/unsubscribe?id=" + recipient.getId()), true);

        for(Map.Entry<String, ByteArrayResource> entry : documentsBytes.entrySet())
            mimeMessageHelper.addAttachment(entry.getKey(), entry.getValue());


        mailSender.send(mimeMessage);
    }
}
