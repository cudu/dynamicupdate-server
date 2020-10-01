package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountVew;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountMapperResolver.class})
public interface AccountMapper extends SpringMapper<Account, AccountVew> {

}
