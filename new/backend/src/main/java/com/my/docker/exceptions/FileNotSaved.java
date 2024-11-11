package com.my.docker.exceptions;

public class FileNotSaved extends RuntimeException {
  public FileNotSaved(String message) {
    super(message);
  }
}
