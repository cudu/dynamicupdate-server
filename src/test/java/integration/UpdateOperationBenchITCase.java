package integration;

import static org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type.CREDIT;

import java.util.concurrent.TimeUnit;
import org.habr.examples.hibernate.dynamicupdate.DynamicUpdateApp;
import org.habr.examples.hibernate.dynamicupdate.mappers.Operation2Mapper;
import org.habr.examples.hibernate.dynamicupdate.mappers.OperationMapper;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;
import org.habr.examples.hibernate.dynamicupdate.models.dto.Operation2View;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationView;
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

  private final int count = 100000;

  @Autowired private OperationMapper operationMapper;
  @Autowired private OperationService operationService;

  @Autowired private Operation2Mapper operation2Mapper;
  @Autowired private Operation2Service operation2Service;

  @Test
  @Order(1)
  void dynamicUpdateOperationValBenchmark() {
    OperationView op = OperationView.builder().type(CREDIT).version((short) 0).val(0).build();
    long actualId = operationService.create(op);
    Operation current = operationService.get(actualId);
    OperationView patch = operationMapper.map(current);
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
    Operation2View op = Operation2View.builder().type(CREDIT).version((short) 0).val(0).build();
    long actualId = operation2Service.create(op);
    Operation2 current = operation2Service.get(actualId);
    Operation2View patch = operation2Mapper.map(current);
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
