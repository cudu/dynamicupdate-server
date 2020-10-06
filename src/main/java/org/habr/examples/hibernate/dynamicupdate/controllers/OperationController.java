package org.habr.examples.hibernate.dynamicupdate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationTable;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController
    implements DynamicUpdateController<Operation, OperationDetails, OperationTable> {

  private final OperationService operationService;

  @Override
  @PatchMapping
  public void update(OperationDetails patch) {
    operationService.update(patch);
  }

  @Override
  @PostMapping
  public Operation create(OperationDetails op) {
    return operationService.create(op);
  }

  @Override
  @GetMapping(
      value = "/{id}",
      produces = {"application/json"})
  public ResponseEntity<OperationDetails> get(@PathVariable long id) {
    return ResponseEntity.ok(operationService.get(id));
  }

  @Override
  @GetMapping
  public ResponseEntity<Page<OperationTable>> getOperations(
      @And({
            @Spec(path = "id", params = "id", spec = Equal.class),
            @Spec(path = "type", params = "type", spec = LikeIgnoreCase.class),
            @Spec(path = "account.name", params = "account.name", spec = LikeIgnoreCase.class),
          })
          Specification<Operation> operationSpecification,
      Pageable pageable) {
    return ResponseEntity.ok(operationService.getBySpecification(operationSpecification, pageable));
  }
}
