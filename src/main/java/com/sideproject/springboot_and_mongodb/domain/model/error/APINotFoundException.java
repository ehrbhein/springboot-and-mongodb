package com.sideproject.springboot_and_mongodb.domain.model.error;

import com.sideproject.springboot_and_mongodb.domain.model.Error;

public class APINotFoundException extends RuntimeException {

  public APINotFoundException(String message) {
    super(message);
  }
}
