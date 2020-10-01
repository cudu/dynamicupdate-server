package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.mapstruct.Mapper;

@Mapper(uses = {OperationMapperResolver.class, AccountMapper.class})
public interface OperationMapper extends SpringMapper<Operation, OperationView> {}
