package com.my.docker.repositories;

import com.my.docker.models.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipientRepo extends JpaRepository<Recipient, UUID> {
    @Query("SELECT u FROM Recipient u WHERE u.email IN :emails")
    List<Recipient> findAllByEmailIn(@Param("emails") List<String> emails);

    List<Recipient> findAllBySubscribed(boolean subscribed);
}
