package org.habr.examples.hibernate.dynamicupdate.mappers;

import lombok.RequiredArgsConstructor;
import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.models.dto.DTO;
import org.habr.examples.hibernate.dynamicupdate.services.DomainEntityService;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@RequiredArgsConstructor
public abstract class DomainMapperResolver<T extends DomainEntity, K extends DTO> {

  private final DomainEntityService<T> service;

  @ObjectFactory
  public T resolve(K dto, @TargetType Class<T> type) {
    return dto != null && dto.getId() != null ? service.get(dto.getId())
        : service.createNewInstance();
  }
}
