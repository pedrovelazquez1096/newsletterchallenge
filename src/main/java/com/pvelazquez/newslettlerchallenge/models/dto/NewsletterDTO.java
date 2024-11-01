package com.pvelazquez.newslettlerchallenge.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsletterDTO {
    private List<UUID> documents;
    private List<String> emails;
}
