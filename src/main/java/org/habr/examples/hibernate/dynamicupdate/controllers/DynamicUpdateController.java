package org.habr.examples.hibernate.dynamicupdate.controllers;

import org.habr.examples.hibernate.dynamicupdate.models.domain.DomainEntity;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.DetailsDTO;
import org.habr.examples.hibernate.dynamicupdate.models.dto.TableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface DynamicUpdateController<T extends DomainEntity, K1 extends DetailsDTO, K2 extends TableDTO> {

  @PatchMapping
  void update(K1 patch);

  @PostMapping
  Operation create(K1 op);

  @GetMapping("/{id}")
  ResponseEntity<K1> get(@PathVariable long id);

  @GetMapping
  ResponseEntity<Page<K2>> getOperations(Specification<T> spec, Pageable pageable);
}
