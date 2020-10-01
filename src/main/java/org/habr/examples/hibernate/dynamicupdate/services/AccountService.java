package org.habr.examples.hibernate.dynamicupdate.services;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends DomainEntityService<Account> {

  public AccountService(
      AccountRepository repository) {
    super(Account.class, repository);
  }


  @Override
  public Account createNewInstance() {
    return new Account();
  }
}
