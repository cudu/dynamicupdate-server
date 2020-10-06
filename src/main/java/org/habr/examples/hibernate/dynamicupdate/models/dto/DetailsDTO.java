package org.habr.examples.hibernate.dynamicupdate.models.dto;

import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;

public interface DetailsDTO<T extends DomainEntity> {
  Long getId();
  T create();
}
