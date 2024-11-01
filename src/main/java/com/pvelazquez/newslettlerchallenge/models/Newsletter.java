package com.pvelazquez.newslettlerchallenge.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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
    @OneToMany(targetEntity = Document.class, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "newsletter_document",
            joinColumns = @JoinColumn(name = "newslettler_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id")
    )
    private List<Document> documents;
    @ManyToMany(targetEntity = Recipient.class, fetch = FetchType.LAZY)
    @JoinTable(
            name = "newsletter_recipíents",
            joinColumns = @JoinColumn(name = "newslettler_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private List<Recipient> recipients;
}
