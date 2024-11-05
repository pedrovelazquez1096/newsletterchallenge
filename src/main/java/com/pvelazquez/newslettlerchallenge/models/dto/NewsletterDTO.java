package com.pvelazquez.newslettlerchallenge.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pvelazquez.newslettlerchallenge.models.Newsletter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsletterDTO {
    private List<UUID> documents;
    private List<String> emails;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SS")
    private LocalDateTime scheduleTime;
    private String subject;
}
