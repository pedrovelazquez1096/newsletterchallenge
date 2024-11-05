package com.pvelazquez.newslettlerchallenge.repositories;

import com.pvelazquez.newslettlerchallenge.models.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface NewsletterRepo extends JpaRepository<Newsletter, UUID> {
    List<Newsletter> findByScheduledTimeBeforeAndStatus(Date date, Newsletter.Status status);

}
