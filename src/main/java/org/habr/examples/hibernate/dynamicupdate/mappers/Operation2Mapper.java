package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2View;
import org.mapstruct.Mapper;

@Mapper(uses = {DomainMapperResolver.class, AccountMapper.class})
public interface Operation2Mapper extends SpringMapper<Operation2, Operation2View> {}
