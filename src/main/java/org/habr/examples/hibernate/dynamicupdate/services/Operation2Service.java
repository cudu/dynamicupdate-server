package org.habr.examples.hibernate.dynamicupdate.services;

import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.mappers.Operation2Mapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2View;
import org.habr.examples.hibernate.dynamicupdate.repositories.Operation2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Operation2Service extends DomainEntityService<Operation2> {

  private final Operation2Mapper operationMapper;

  public Operation2Service(
      Operation2Repository repository,
      Operation2Mapper operationMapper) {
    super(Operation2.class, repository);
    this.operationMapper = operationMapper;
  }

  @Transactional
  public Operation2 get(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new DynamicUpdateEntityNotFoundException(Operation.class, id));
  }

  @Transactional(readOnly = true)
  public Operation2 getReference(long id) {
    return repository.getOne(id);
  }

  @Transactional(readOnly = true)
  public Page<Operation2> getPage(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Page<Operation2> getBySpecification(Specification<Operation2> specification, Pageable pageable) {
    return repository.findAll(specification, pageable);
  }

  @Transactional
  public Long create(Operation2View request) {
    Operation2 op = operationMapper.map(request);
    op = repository.save(op);
    return op.getId();
  }

  @Transactional
  public void update(Operation2View patch) {
    operationMapper.map(patch);
  }
}
