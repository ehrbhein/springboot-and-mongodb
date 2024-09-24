package com.sideproject.springboot_and_mongodb.domain.model.error;

public class APIBadRequestException extends RuntimeException{


  public APIBadRequestException(String message) {
    super(message);
  }

}
