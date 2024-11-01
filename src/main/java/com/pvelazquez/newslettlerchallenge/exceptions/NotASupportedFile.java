package com.pvelazquez.newslettlerchallenge.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotASupportedFile extends Exception{
    public NotASupportedFile(String message){
        super(message);
    }
}
