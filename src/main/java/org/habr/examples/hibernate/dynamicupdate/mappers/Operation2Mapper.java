package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2Details;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2Table;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DomainMapperResolver.class, AccountMapper.class})
public interface Operation2Mapper extends SpringMapper<Operation2, Operation2Details, Operation2Table> {

  @Override
  @Mapping(target = "accountName", source = "account.name")
  Operation2Table mapToTable(Operation2 entity);
}
