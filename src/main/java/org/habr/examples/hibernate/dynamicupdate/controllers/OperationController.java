package org.habr.examples.hibernate.dynamicupdate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
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
public class OperationController {

  private final OperationService operationService;
  private final OperationMapper operationMapper;

  @PostMapping
  public Long create(OperationView op) {
    return operationService.create(op);
  }

  @PatchMapping
  public void update(OperationView patch) {
    operationService.update(patch);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OperationView> get(@PathVariable long id) {
    return ResponseEntity.ok(operationMapper.map(operationService.get(id)));
  }


}
