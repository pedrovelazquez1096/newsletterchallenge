package com.pvelazquez.newslettlerchallenge.repositories;

import com.pvelazquez.newslettlerchallenge.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsletterRepo extends JpaRepository<Newsletter, UUID> {
}
