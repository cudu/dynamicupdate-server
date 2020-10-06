package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DomainMapperResolver.class, AccountMapper.class})
public interface OperationMapper extends SpringMapper<Operation, OperationDetails, OperationTable> {

  @Override
  @Mapping(target = "accountName", source = "account.name")
  OperationTable mapToTable(Operation entity);
}
