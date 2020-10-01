package org.habr.examples.hibernate.dynamicupdate.exceptions;

import org.springframework.http.HttpStatus;

public class DynamicUpdateEntityNotFoundException extends DynamicUpdateException {

  public DynamicUpdateEntityNotFoundException(Class<?> entityClass, Long id) {
    super(
        HttpStatus.NOT_FOUND,
        String.format(
            "Entity '%s' with id=[%d] not found", entityClass.getSimpleName().toLowerCase(), id));
  }
}
