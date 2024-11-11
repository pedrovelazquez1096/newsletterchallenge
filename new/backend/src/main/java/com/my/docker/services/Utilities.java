package com.my.docker.services;

import org.springframework.stereotype.Service;

@Service
public class Utilities {

    public String generateAlphanumericString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        final String alphanumericChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * alphanumericChars.length());
            char randomChar = alphanumericChars.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    public boolean isPdfOrPng(String fileName) {
        if (fileName == null) {
            return false;
        }

        return fileName.toLowerCase().matches(".*\\.(pdf|png)$");
    }
}
