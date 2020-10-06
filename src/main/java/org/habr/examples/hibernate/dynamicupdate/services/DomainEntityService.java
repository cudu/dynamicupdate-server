package org.habr.examples.hibernate.dynamicupdate.services;

import lombok.RequiredArgsConstructor;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.mappers.SpringMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.models.dto.DetailsDTO;
import org.habr.examples.hibernate.dynamicupdate.models.dto.TableDTO;
import org.habr.examples.hibernate.dynamicupdate.repositories.DomainEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class DomainEntityService<T extends DomainEntity, K extends DetailsDTO, K2 extends TableDTO> {

  private final Class<T> _class;
  protected final SpringMapper<T, K, K2> mapper;
  protected final DomainEntityRepository<T> repository;

  @Transactional
  public K get(long id) {
    T e = repository
        .findById(id)
        .orElseThrow(() -> new DynamicUpdateEntityNotFoundException(_class, id));
    return mapper.mapToDetails(e);
  }

  @Transactional(readOnly = true)
  public Page<K2> getBySpecification(
      Specification<T> specification, Pageable pageable) {
    return mapper.map(repository.findAll(specification, pageable));
  }
}
