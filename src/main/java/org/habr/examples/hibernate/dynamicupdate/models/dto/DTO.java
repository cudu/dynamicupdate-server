package org.habr.examples.hibernate.dynamicupdate.models.dto;

import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;

public interface DTO<T extends DomainEntity> {
  Long getId();
  T create();
}
