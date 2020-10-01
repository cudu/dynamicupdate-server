package org.habr.examples.hibernate.dynamicupdate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DynamicUpdateException extends ResponseStatusException {

  public DynamicUpdateException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  public DynamicUpdateException(HttpStatus status, String message) {
    super(status, message);
  }
}
