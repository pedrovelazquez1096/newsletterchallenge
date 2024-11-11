package com.my.docker.controllers;

import com.my.docker.exceptions.FileNotSaved;
import com.my.docker.exceptions.NotASupportedFile;
import com.my.docker.exceptions.NotFoundException;
import com.my.docker.services.DocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/documents")
@CrossOrigin(origins = "*")
@Slf4j
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public UUID saveDocument(@RequestParam("document") MultipartFile file) throws NotASupportedFile, FileNotSaved {
        return documentService.saveDocument(file).getId();
    }

    @DeleteMapping
    public void deleteDocument(@RequestParam UUID id) throws NotFoundException {
        documentService.deleteDocument(id);
    }
}