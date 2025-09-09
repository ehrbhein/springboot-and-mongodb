package com.sideproject.springboot_and_mongodb.domain.model.error;

public class APIConflictException extends RuntimeException {

  public APIConflictException(String message) {
    super(message);
  }
}
