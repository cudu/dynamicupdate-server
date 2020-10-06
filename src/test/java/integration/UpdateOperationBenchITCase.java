package integration;

import static org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type.CREDIT;

import java.util.concurrent.TimeUnit;
import org.habr.examples.hibernate.dynamicupdate.DynamicUpdateApp;
import org.habr.examples.hibernate.dynamicupdate.mappers.Operation2Mapper;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2Details;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationDetails;
import org.habr.examples.hibernate.dynamicupdate.services.Operation2Service;
import org.habr.examples.hibernate.dynamicupdate.services.OperationService;
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
@SpringBootTest(
    classes = DynamicUpdateApp.class,
    properties = {"logging.level.ROOT=ERROR"})
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class UpdateOperationBenchITCase {

  private final int count = 10000;

  @Autowired private OperationMapper operationMapper;
  @Autowired private OperationService operationService;

  @Autowired private Operation2Mapper operation2Mapper;
  @Autowired private Operation2Service operation2Service;

  @Test
  @Order(1)
  void dynamicUpdateOperationValBenchmark() {
    OperationDetails op = OperationDetails.builder().type(CREDIT).version((short) 0).val(0).build();
    long actualId = operationService.create(op).getId();
    OperationDetails patch = operationService.get(actualId);
    short prevVersion = patch.getVersion();

    long start = System.nanoTime();
    for (int i = 0; i < count; i++) {
      prevVersion = (short) (prevVersion + 1);
      patch.setVal(i);
      patch.setVersion(prevVersion);
      operationService.update(patch);
    }
    long end = System.nanoTime();

    long seconds = TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS);
    System.out.println("~" + (count / seconds) + " operation updates  per/s");
  }

  @Test
  @Order(2)
  void updateOperation2ValBenchmark() {
    Operation2Details op = Operation2Details.builder().type(CREDIT).version((short) 0).val(0).build();
    Operation2 op2 = operation2Service.create(op);
    Operation2Details patch = operation2Service.get(op2.getId());
    short prevVersion = patch.getVersion();

    long start = System.nanoTime();
    for (int i = 0; i < count; i++) {
      prevVersion = (short) (prevVersion + 1);
      patch.setVal(i);
      patch.setVersion(prevVersion);
      operation2Service.update(patch);
    }
    long end = System.nanoTime();

    long seconds = TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS);
    System.out.println("~" + (count / seconds) + " operation2 updates per/s");
  }
}
