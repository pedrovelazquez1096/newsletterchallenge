package com.pvelazquez.newslettlerchallenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.pvelazquez.newslettlerchallenge.models.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatcherUtil {
    private final ObjectMapper objectMapper;

    public PatcherUtil(@Autowired ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public Recipient applyPatchRecipient(JsonPatch patch, Recipient recipient) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(recipient, JsonNode.class));
        return objectMapper.treeToValue(patched, Recipient.class);
    }
}
