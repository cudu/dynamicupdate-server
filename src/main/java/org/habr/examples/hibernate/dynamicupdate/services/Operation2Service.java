package org.habr.examples.hibernate.dynamicupdate.services;

import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.mappers.Operation2Mapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2Details;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2Table;
import org.habr.examples.hibernate.dynamicupdate.repositories.Operation2Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Operation2Service extends DomainEntityService<Operation2, Operation2Details, Operation2Table> {

  public Operation2Service(
      Operation2Repository repository,
      Operation2Mapper operationMapper) {
    super(Operation2.class, operationMapper, repository);
  }

  @Transactional
  public Operation2Details get(long id) {
    Operation2 op = repository
        .findById(id)
        .orElseThrow(() -> new DynamicUpdateEntityNotFoundException(Operation.class, id));
    return mapper.mapToDetails(op);
  }

  @Transactional(readOnly = true)
  public Operation2 getReference(long id) {
    return repository.getOne(id);
  }

  @Transactional(readOnly = true)
  public Page<Operation2Table> getPage(Pageable pageable) {
    return mapper.map(repository.findAll(pageable));
  }

  @Transactional(readOnly = true)
  public Page<Operation2Table> getBySpecification(Specification<Operation2> specification, Pageable pageable) {
    return mapper.map(repository.findAll(specification, pageable));
  }

  @Transactional
  public Operation2 create(Operation2Details request) {
    Operation2 op = mapper.map(request);
    return repository.save(op);
  }

  @Transactional
  public void update(Operation2Details patch) {
    mapper.map(patch);
  }
}
