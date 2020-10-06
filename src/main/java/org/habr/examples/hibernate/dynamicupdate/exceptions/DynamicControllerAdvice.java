package org.habr.examples.hibernate.dynamicupdate.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.habr.examples.hibernate.dynamicupdate.exceptions.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class DynamicControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(
      value = {DynamicUpdateException.class, DynamicUpdateEntityNotFoundException.class})
  protected ResponseEntity<ErrorResponse> handleCheckedError(
      ResponseStatusException ex, WebRequest request) {
    return ResponseEntity.status(ex.getStatus()).body(ErrorResponse.builder().message(ex.getReason()).build());
  }

  @ExceptionHandler(
      value = {RuntimeException.class})
  protected ResponseEntity<ErrorResponse> handleUncheckedError(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = ex.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder().message(bodyOfResponse).build());
  }
}
