package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.habr.examples.hibernate.dynamicupdate.services.DomainEntityService;
import org.springframework.stereotype.Component;

@Component
public class OperationMapperResolver extends DomainMapperResolver<Operation, OperationView> {

  public OperationMapperResolver(
      DomainEntityService<Operation> service) {
    super(service);
  }
}
