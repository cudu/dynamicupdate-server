package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationMapperResolver {

  @Autowired
  private OperationService service;

  @ObjectFactory
  public Operation resolve(OperationView dto, @TargetType Class<Operation> type) {
    return dto != null && dto.getId() != null ? service.get(dto.getId()) : new Operation();
  }
}
