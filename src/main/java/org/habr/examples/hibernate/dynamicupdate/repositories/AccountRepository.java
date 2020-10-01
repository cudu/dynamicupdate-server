package org.habr.examples.hibernate.dynamicupdate.repositories;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends DomainEntityRepository<Account> {

}
