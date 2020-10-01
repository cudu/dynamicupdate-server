package org.habr.examples.hibernate.dynamicupdate.mappers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountVew;
import org.habr.examples.hibernate.dynamicupdate.services.DomainEntityService;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperResolver extends DomainMapperResolver<Account, AccountVew> {

  public AccountMapperResolver(
      DomainEntityService<Account> service) {
    super(service);
  }
}
