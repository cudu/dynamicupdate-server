package org.habr.examples.hibernate.dynamicupdate.repositories;

import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DomainEntityRepository<T extends DomainEntity> extends JpaRepository<T, Long>,
    JpaSpecificationExecutor<T> {

}
