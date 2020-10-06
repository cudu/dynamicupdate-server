package org.habr.examples.hibernate.dynamicupdate.mappers;

import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.models.dto.DetailsDTO;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DomainMapperResolver {

  private final EntityManager entityManager;

  @ObjectFactory
  public <T extends DomainEntity, K extends DetailsDTO<T>> T resolve(K dto, @TargetType Class<T> type) {
    return dto != null && dto.getId() != null ?
        entityManager.find(type, dto.getId()) :
        dto.create();
  }
}
