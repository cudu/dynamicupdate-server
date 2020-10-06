package integration;

import static org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type.CREDIT;
import static org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type.DEBIT;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.habr.examples.hibernate.dynamicupdate.DynamicUpdateApp;
import org.habr.examples.hibernate.dynamicupdate.models.dto.AccountDetails;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("h2")
@SpringJUnitConfig
@SpringBootTest(classes = DynamicUpdateApp.class)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class OperationServiceITCase {

  private Long actualId;
  private short expectedVersion;

  @Autowired private OperationService operationService;

  @Test
  @Order(1)
  void successfulCreateCreditOperation() {
    OperationDetails op = OperationDetails.builder().type(CREDIT).version((short) 0).val(10).build();
    actualId = operationService.create(op).getId();
    OperationDetails current = operationService.get(actualId);
    expectedVersion = current.getVersion();
    assertNotNull(actualId);
  }

  @Test
  @Order(2)
  void successUpdateToDebitType() {
    OperationDetails patch = operationService.get(actualId);
    patch.setType(DEBIT);
    operationService.update(patch);
    expectedVersion++;
    OperationDetails op = operationService.get(actualId);
    assertAll(
        () -> Assertions.assertEquals(op.getType(), DEBIT),
        () -> Assertions.assertEquals(expectedVersion, op.getVersion()));
  }

  @Test
  @Order(3)
  void failureUpdateWithDebitType() {
    OperationDetails patch = operationService.get(actualId);
    patch.setType(DEBIT);
    operationService.update(patch);
    OperationDetails op = operationService.get(actualId);
    assertAll(
        () -> Assertions.assertEquals(op.getType(), DEBIT),
        () -> Assertions.assertEquals(expectedVersion, op.getVersion()));
  }

  @Test
  @Order(4)
  void successfulUpdateWithNewAccount() {
    OperationDetails patch = operationService.get(actualId);
    patch.setType(CREDIT);
    patch.setAccount(AccountDetails.builder().name("account_1").build());
    operationService.update(patch);
    expectedVersion++;
    OperationDetails op = operationService.get(actualId);
    assertAll(
        () -> Assertions.assertNotNull(op.getAccount()),
        () -> Assertions.assertEquals(expectedVersion, op.getVersion()));
  }

  @Test
  @Order(5)
  void successfulUpdateAccountName() {
    OperationDetails patch = operationService.get(actualId);
    String expectedAccountName = "account_2";
    patch.getAccount().setName(expectedAccountName);
    operationService.update(patch);
    OperationDetails op = operationService.get(actualId);
    assertAll(
        () -> Assertions.assertEquals(expectedAccountName, op.getAccount().getName()),
        () -> Assertions.assertEquals(expectedVersion, op.getVersion()));
  }

  @Test
  @Order(6)
  void successfulDeleteLinkWithAccount() {
    OperationDetails patch = operationService.get(actualId);
    patch.setAccount(null);
    operationService.update(patch);
    expectedVersion++;
    OperationDetails op = operationService.get(actualId);
    assertAll(
        () -> Assertions.assertNull(op.getAccount()),
        () -> Assertions.assertEquals(expectedVersion, op.getVersion()));
  }
}
