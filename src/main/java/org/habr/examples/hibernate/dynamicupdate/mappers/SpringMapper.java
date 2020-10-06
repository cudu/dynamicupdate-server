package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.models.dto.DetailsDTO;
import org.habr.examples.hibernate.dynamicupdate.models.dto.TableDTO;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@MapperConfig(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
    componentModel = "spring")
public interface SpringMapper<T extends DomainEntity, K1 extends DetailsDTO, K2 extends TableDTO> {

  T map(K1 view);

  K1 mapToDetails(T entity);

  K2 mapToTable(T entity);

  default Page<K2> map(Page<T> page) {
    return page.map(this::mapToTable);
  }

}
