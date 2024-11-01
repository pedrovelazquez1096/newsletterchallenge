package com.pvelazquez.newslettlerchallenge.repositories;

import com.pvelazquez.newslettlerchallenge.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepo extends JpaRepository<Document, UUID> {
}
