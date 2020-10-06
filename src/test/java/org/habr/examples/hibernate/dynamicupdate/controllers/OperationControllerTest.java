package org.habr.examples.hibernate.dynamicupdate.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicControllerAdvice;
import org.habr.examples.hibernate.dynamicupdate.exceptions.DynamicUpdateEntityNotFoundException;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
class OperationControllerTest {

  @Mock OperationService operationService;

  @InjectMocks private OperationController operationController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    when(operationService.get(1)).thenReturn(createOperationDetails(1));
    when(operationService.get(10))
        .thenThrow(new DynamicUpdateEntityNotFoundException(Operation.class, 10L));
    when(operationService.get(100)).thenThrow(new RuntimeException("Something goes wrong..."));
    when(operationService.get(1000)).thenThrow(new ConstraintViolationException("Constraint violation exception", new SQLException(), "constraint"));
    operationController = new OperationController(operationService);
    mockMvc =
        MockMvcBuilders.standaloneSetup(operationController)
            .setControllerAdvice(new DynamicControllerAdvice())
            .build();
  }

  private OperationDetails createOperationDetails(long id) {
    return OperationDetails.builder().id(id).type(Type.CREDIT).version((short) 0).val(100).build();
  }

  @Test
  void ifRequestForOperationDetailsThenResponseOkStatusWithJsonOperation() throws Exception {
    String expectedContent = "{\"id\":1,\"version\":0,\"val\":100,\"type\":\"CREDIT\",\"account\":null}";
    assertSuccessResponse(mockMvc.perform(get("/operations/1")), expectedContent);
  }

  @Test
  void ifThrowNotFoundExceptionThenResponseNotFoundStatusWithErrorResponse() throws Exception {
    String expectedContent = "{\"message\":\"Entity 'operation' with id=[10] not found\"}";
    assertNotfoundResponse(mockMvc.perform(get("/operations/10")), expectedContent);
  }

  @Test
  void ifThrowRuntimeExceptionThenResponseInternalServerErrorWithErrorResponse() throws Exception {
    String expectedContent = "{\"message\":\"Something goes wrong...\"}";
    assertInternalErrorResponse(mockMvc.perform(get("/operations/100")), expectedContent);
  }

  @Test
  void ifThrowSomeKindOfChildRuntimeExceptionThenResponseInternalServerErrorWithErrorResponse() throws Exception {
    String expectedContent = "{\"message\":\"Constraint violation exception\"}";
    assertInternalErrorResponse(mockMvc.perform(get("/operations/1000")), expectedContent);
  }

  private void assertSuccessResponse(ResultActions resultActions, String expectedContent) throws Exception {
    assertResponseWithStatus(resultActions, HttpStatus.OK, expectedContent);
  }

  private void assertNotfoundResponse(ResultActions resultActions, String expectedContent) throws Exception {
    assertResponseWithStatus(resultActions, HttpStatus.NOT_FOUND, expectedContent);
  }

  private void assertInternalErrorResponse(ResultActions resultActions, String expectedContent) throws Exception {
    assertResponseWithStatus(resultActions, HttpStatus.INTERNAL_SERVER_ERROR, expectedContent);
  }

  private void assertResponseWithStatus(ResultActions resultActions, HttpStatus status, String expectedContent)
      throws Exception {
    resultActions
        .andExpect(status().is(status.value()))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(expectedContent));
  }
}
