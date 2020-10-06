package org.habr.examples.hibernate.dynamicupdate.services;

import org.habr.examples.hibernate.dynamicupdate.mappers.AccountMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountDetails;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountTable;
import org.habr.examples.hibernate.dynamicupdate.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends DomainEntityService<Account, AccountDetails, AccountTable> {

  public AccountService(
      AccountRepository repository, AccountMapper accountMapper) {
    super(Account.class, accountMapper, repository);
  }
}
