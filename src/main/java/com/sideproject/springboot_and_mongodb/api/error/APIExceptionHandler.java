package com.sideproject.springboot_and_mongodb.api.error;

import com.sideproject.springboot_and_mongodb.domain.model.Error;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {

  @ExceptionHandler(APIConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handle409(APIConflictException exception) {
    System.out.println(" ({})");

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new Error(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage()));
  }
}
