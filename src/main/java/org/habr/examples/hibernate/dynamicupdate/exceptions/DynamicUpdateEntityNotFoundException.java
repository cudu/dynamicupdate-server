package org.habr.examples.hibernate.dynamicupdate.exceptions;

public class DynamicUpdateEntityNotFoundException extends DynamicUpdateException {

  public DynamicUpdateEntityNotFoundException(Class<?> entityClass, Long id) {
    super(String
        .format("Entity '%s' with id=[%d] not found", entityClass.getSimpleName().toLowerCase(), id));
  }
}
