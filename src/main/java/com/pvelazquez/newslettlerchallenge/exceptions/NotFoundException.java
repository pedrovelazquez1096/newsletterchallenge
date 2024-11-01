package com.pvelazquez.newslettlerchallenge.exceptions;

import lombok.NoArgsConstructor;

import java.nio.file.NotDirectoryException;

@NoArgsConstructor
public class NotFoundException extends Exception{
    public NotFoundException(String message){
        super(message);
    }
}
