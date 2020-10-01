package org.habr.examples.hibernate.dynamicupdate.repositories;

import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends DomainEntityRepository<Operation> {

}
