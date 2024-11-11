package com.my.docker.repositories;

import com.my.docker.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsletterRepo extends JpaRepository<Newsletter, UUID> {
    List<Newsletter> findAllByStatus(Newsletter.Status status);
}
