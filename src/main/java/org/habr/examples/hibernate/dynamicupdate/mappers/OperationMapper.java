package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.mapstruct.Mapper;

@Mapper(uses = {DomainMapperResolver.class, AccountMapper.class})
public interface OperationMapper extends SpringMapper<Operation, OperationView> {}
