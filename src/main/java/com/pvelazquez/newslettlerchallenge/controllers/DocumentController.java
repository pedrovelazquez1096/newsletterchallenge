package com.pvelazquez.newslettlerchallenge.controllers;

import com.pvelazquez.newslettlerchallenge.exceptions.FileNotSaved;
import com.pvelazquez.newslettlerchallenge.exceptions.NotASupportedFile;
import com.pvelazquez.newslettlerchallenge.exceptions.NotFoundException;
import com.pvelazquez.newslettlerchallenge.models.Document;
import com.pvelazquez.newslettlerchallenge.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public Document saveDocument(@RequestParam("document") MultipartFile file) throws NotASupportedFile, FileNotSaved {
        return documentService.saveDocument(file);
    }

    @DeleteMapping
    public void deleteDocument(@RequestParam UUID id) throws NotFoundException {
        documentService.deleteDocument(id);
    }
}
