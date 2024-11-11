package com.my.docker.services;

import com.my.docker.models.Document;
import com.my.docker.models.Recipient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmailSenderService {
    private final AzureBlobClient client;
    private final EmailBodyService emailBodyService;

    private  String hostName = "backend_mailhog";
    private  String hostPort = "1025";
    @Autowired
    public EmailSenderService(AzureBlobClient client, EmailBodyService emailBodyService){
        this.client = client;
        this.emailBodyService = emailBodyService;

    }
    private JavaMailSenderImpl createJavaMailSenderImpl() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(hostName);
        mailSender.setPort(Integer.parseInt(hostPort));
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");

        return  mailSender;
    }

    private MimeMessage createMimeMessage(JavaMailSenderImpl mailSender, String subject, Recipient recipient, HashMap<String, ByteArrayResource> documentsBytes) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);


        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("newsletter@home.com");
        mimeMessageHelper.setTo(recipient.getEmail());

        mimeMessageHelper.setText(emailBodyService.emailBodyWithUnsubcribe("http://localhost:8080/api/v1/recipient/unsubscribe?id=" + recipient.getId()), true);

        for(Map.Entry<String, ByteArrayResource> entry : documentsBytes.entrySet())
            mimeMessageHelper.addAttachment(entry.getKey(), entry.getValue());

        return mimeMessage;
    }

    public void prepareEmail(String subject, List<Recipient> recipientList, List<Document> documents) throws MessagingException {
        HashMap<String, ByteArrayResource> documentsBytes = createDocumentsBytes(documents);

        for(Recipient recipient : recipientList)
            sendEmail(subject, recipient, documentsBytes);

    }
    public HashMap<String, ByteArrayResource> createDocumentsBytes(List<Document> documents){
        HashMap<String, ByteArrayResource> documentsBytes = new HashMap<>();

        for(Document document : documents){
            String base64 = client.downloadFile(document.getUrl());

            ByteArrayResource byteArrayResource = new ByteArrayResource(Base64.getDecoder().decode(base64));

            documentsBytes.put(document.getFileName(), byteArrayResource);
        }
        return documentsBytes;
    }
    public void sendEmail(String subject, Recipient recipient, HashMap<String, ByteArrayResource> documentsBytes) throws MessagingException {
        JavaMailSenderImpl mailSender = createJavaMailSenderImpl();
        MimeMessage mimeMessage = createMimeMessage(mailSender, subject, recipient, documentsBytes);
        mailSender.send(mimeMessage);
    }

}