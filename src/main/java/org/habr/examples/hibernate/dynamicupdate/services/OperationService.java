package org.habr.examples.hibernate.dynamicupdate.services;

import lombok.RequiredArgsConstructor;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.habr.examples.hibernate.dynamicupdate.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationService {

  private final OperationRepository repository;
  private final OperationMapper operationMapper;

  public OperationService(
      OperationRepository repository,
      OperationMapper operationMapper) {
    this.repository = repository;
    this.operationMapper = operationMapper;
  }

  @Transactional
  public Operation get(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new DynamicUpdateEntityNotFoundException(Operation.class, id));
  }

  @Transactional(readOnly = true)
  public Operation getReference(long id) {
    return repository.getOne(id);
  }

  @Transactional(readOnly = true)
  public Page<Operation> getPage(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Transactional(readOnly = true)
  public Page<Operation> getBySpecification(Specification<Operation> specification, Pageable pageable) {
    return repository.findAll(specification, pageable);
  }

  @Transactional
  public Long create(OperationView request) {
    Operation op = operationMapper.map(request);
    op = repository.save(op);
    return op.getId();
  }

  @Transactional
  public void update(OperationView patch) {
    operationMapper.map(patch);
  }
}
