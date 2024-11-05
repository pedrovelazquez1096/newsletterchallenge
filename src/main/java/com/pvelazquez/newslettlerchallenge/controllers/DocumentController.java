package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.exceptions.FileNotSaved;
import com.pvelazquez.newslettlerchallenge.exceptions.NotASupportedFile;
import com.pvelazquez.newslettlerchallenge.exceptions.NotFoundException;
import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*")
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public List<UUID> saveDocument(@RequestParam("document") List<MultipartFile> files) throws NotASupportedFile, FileNotSaved {
        List<UUID> documents = new ArrayList<>();

        for(MultipartFile file : files){
            documents.add(documentService.saveDocument(file).getId());
        }
        return documents;
    }

    @DeleteMapping
    public void deleteDocument(@RequestParam UUID id) throws NotFoundException {
        documentService.deleteDocument(id);
    }
}
