package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountView;
import org.mapstruct.Mapper;

@Mapper(uses = {DomainMapperResolver.class})
public interface AccountMapper extends SpringMapper<Account, AccountView> {

}
