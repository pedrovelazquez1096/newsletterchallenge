package com.my.docker.services;

import com.my.docker.exceptions.FileNotSaved;
import com.my.docker.exceptions.NotASupportedFile;
import com.my.docker.exceptions.NotFoundException;
import com.my.docker.models.Document;
import com.my.docker.repositories.DocumentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentService {
    private final DocumentRepo documentRepo;
    private final AzureBlobClient client;
    private final Utilities utilities;

    public Document saveDocument(MultipartFile file) throws NotASupportedFile, FileNotSaved {
        if(!utilities.isPdfOrPng(file.getOriginalFilename()))
            throw new NotASupportedFile("Only PDF or PNG accepted");

        String url = client.saveFile(file);
        if(url == null)
            throw new FileNotSaved("File not saved");

        Document document = Document.builder()
                .url(url)
                .fileName(file.getOriginalFilename())
                .build();

        return documentRepo.save(document);
    }

    public void deleteDocument(UUID id) throws NotFoundException {
        Optional<Document> documentOptional = documentRepo.findById(id);

        if(documentOptional.isEmpty())
            throw new NotFoundException("Document not found");

        client.deleteFile(documentOptional.get().getUrl());

        documentRepo.deleteById(id);
    }

    public List<Document> getAllDocuments(List<UUID> ids){
        return documentRepo.findAllById(ids);
    }
}
