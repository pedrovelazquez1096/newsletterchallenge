package com.pvelazquez.newslettlerchallenge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Document {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;
    private String url;
    private String fileName;
}
