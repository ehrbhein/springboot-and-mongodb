package com.sideproject.springboot_and_mongodb.api.error;

import com.sideproject.springboot_and_mongodb.domain.model.Error;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APINotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler {

  @ExceptionHandler(APIConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handle409(APIConflictException exception) {
    log.warn("{}", exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new Error(String.valueOf(HttpStatus.CONFLICT.value()), exception.getMessage()));
  }


  @ExceptionHandler(APINotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handle404(APINotFoundException exception) {
    log.warn("{}", exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new Error(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage()));
  }
}
