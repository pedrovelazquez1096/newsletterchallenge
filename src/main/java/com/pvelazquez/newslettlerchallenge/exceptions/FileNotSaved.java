package com.pvelazquez.newslettlerchallenge.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileNotSaved extends Exception{
    public FileNotSaved(String message){
        super(message);
    }
}
