package com.pvelazquez.newslettlerchallenge.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Newsletter {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    @OneToMany(targetEntity = Document.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name = "newsletter_document",
            joinColumns = @JoinColumn(name = "newslettler_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<Document> documents;
    @ManyToMany(targetEntity = Recipient.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "newsletter_recipíents",
            joinColumns = @JoinColumn(name = "newslettler_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private List<Recipient> recipients;
    private String subject;
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledTime;
    @Enumerated(EnumType.STRING)
    private Newsletter.Status status;

    public enum Status {
        SCHEDULED,
        SENT,
        CANCELLED
    }
}
