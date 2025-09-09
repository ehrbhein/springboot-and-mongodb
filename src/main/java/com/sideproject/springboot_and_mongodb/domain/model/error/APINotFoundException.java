package com.sideproject.springboot_and_mongodb.domain.model.error;


public class APINotFoundException extends RuntimeException {
  public APINotFoundException(String message) {
    super(message);
  }
}
