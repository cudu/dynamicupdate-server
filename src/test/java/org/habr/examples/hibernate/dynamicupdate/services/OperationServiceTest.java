package org.habr.examples.hibernate.dynamicupdate.services;

import static org.mockito.Mockito.when;

import java.util.Optional;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapperImpl;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.repositories.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OperationServiceTest {

  @Mock private OperationRepository operationRepository;

  private OperationMapper operationMapper;

  @InjectMocks private OperationService operationService;

  @BeforeEach
  public void setUp() {
    when(operationRepository.findById(1L))
        .thenReturn(Optional.of(Operation.builder().id(1L).version((short) 0).build()));
    when(operationRepository.findById(10L))
        .thenReturn(Optional.empty());
    operationMapper = new OperationMapperImpl();
    operationService = new OperationService(operationRepository, operationMapper);
  }

 /* @Test
  public void ifTryToFindByIdThenSuccessfulGetOperationDetails() {
    long expectedId = 1;
    OperationDetails op = operationService.get(expectedId);
    Assertions.assertEquals(op.getId(), expectedId);
  }*/

  @Test
  public void ifTryToFindByIdThenThrowNotFoundException() {
    long expectedId = 10;
    Assertions.assertThrows(DynamicUpdateEntityNotFoundException.class, () -> {
      OperationDetails op = operationService.get(expectedId);
    });
  }

  @Test
  void getPage() {}
}
