package org.habr.examples.hibernate.dynamicupdate.services;

import lombok.RequiredArgsConstructor;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.repositories.DomainEntityRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class DomainEntityService<T extends DomainEntity> {

  private final Class<T> _class;
  protected final DomainEntityRepository<T> repository;

  @Transactional
  public T get(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new DynamicUpdateEntityNotFoundException(_class, id));
  }
}
