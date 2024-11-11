package com.my.docker.repositories;

import com.my.docker.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepo extends JpaRepository<Document, UUID> {
}
