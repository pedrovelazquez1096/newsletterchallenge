package com.pvelazquez.newslettlerchallenge.services;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Objects;

@Service
@Slf4j
public class AzureBlobClient {
    @Autowired
    private Utilities utilities;

    @Value("${azure.blob.storage.connectionstring}")
    private String connectionString;
    @Value("${azure.blob.storage.container}")
    private String containerName;
    @Value("${base.url.azure.blob.storage}")
    private String URL_BLOB_STORAGE;

    private BlobServiceClient blobServiceClient;
    private BlobContainerClient containerClient;

    public String saveFile(MultipartFile file) {
        String result = null;
        try {
            String blobName = "documents/" + utilities.generateAlphanumericString(18) + "_" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "");

            BlobClient blobClient = getBlobClient(blobName);
            byte[] data = file.getBytes();
            blobClient.upload(new ByteArrayInputStream(data), data.length);

            result =  URL_BLOB_STORAGE + "/devstoreaccount1/" + containerName + "/" + blobName;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
    public String downloadFile(String url){
        String result = null;
        try {
            BlobClient blobClient = getBlobClient(url.split("/" + containerName + "/")[1]);
            BinaryData data = blobClient.downloadContent();
            byte[] downloadedContent = data.toBytes();

            // Encode byte array to Base64 string
            result = Base64.getEncoder().encodeToString(downloadedContent);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return result;
    }

    public void deleteFile(String url){
        try{
            String blobName = url.split("/documents/")[1];
            BlobClient blobClient = getBlobClient(blobName);
            if (blobClient.exists()) {
                blobClient.delete();
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private BlobClient getBlobClient(String url) {
        if (blobServiceClient == null)
            blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

        if (containerClient == null)
            containerClient = blobServiceClient.getBlobContainerClient(containerName);

        return containerClient.getBlobClient(url);
    }
}
