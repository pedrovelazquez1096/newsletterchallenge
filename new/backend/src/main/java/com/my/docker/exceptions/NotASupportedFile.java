package com.my.docker.exceptions;

public class NotASupportedFile extends RuntimeException {
  public NotASupportedFile(String message) {
    super(message);
  }
}
