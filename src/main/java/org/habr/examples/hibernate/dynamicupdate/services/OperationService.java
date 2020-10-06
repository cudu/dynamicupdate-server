package org.habr.examples.hibernate.dynamicupdate.services;

import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationTable;
import org.habr.examples.hibernate.dynamicupdate.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationService extends DomainEntityService<Operation, OperationDetails, OperationTable> {

  public OperationService(OperationRepository repository, OperationMapper operationMapper) {
    super(Operation.class, operationMapper, repository);
  }

  @Transactional(readOnly = true)
  public Operation getReference(long id) {
    return repository.getOne(id);
  }

  @Transactional(readOnly = true)
  public Page<OperationTable> getPage(Pageable pageable) {
    return mapper.map(repository.findAll(pageable));
  }

  @Transactional
  public Operation create(OperationDetails request) {
    Operation op = mapper.map(request);
    return repository.save(op);
  }

  @Transactional
  public void update(OperationDetails patch) {
    mapper.map(patch);
  }
}
