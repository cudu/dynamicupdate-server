package org.habr.examples.hibernate.dynamicupdate.mappers;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type;
import org.habr.examples.hibernate.dynamicupdate.models.dto.OperationTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

class OperationMapperTest {

  private OperationMapper operationMapper = new OperationMapperImpl();

  @BeforeEach
  void setUp() {}

  @Test
  void successfulMapEntityToTableDTO() {
    int i = 1;
    Operation op = createOperation(i);

    OperationTable opt = operationMapper.mapToTable(op);
    assertAll(
        () -> Assertions.assertEquals(opt.getType(), "DEBIT"),
        () -> Assertions.assertEquals(opt.getAccountName(), "Account" + i));
  }

  @Test
  void successfulMapToPageForTable() {
    Page<Operation> page = new PageImpl<>(createOpTable(10));
    Page<OperationTable> p = operationMapper.map(page);
    assertAll(
        () -> Assertions.assertEquals(p.getSize(), 10),
        () -> Assertions.assertEquals(p.getContent().get(1).getType(), "DEBIT"),
        () -> Assertions.assertEquals(p.getContent().get(2).getType(), "CREDIT"),
        () -> Assertions.assertEquals(p.getContent().get(1).getAccountName(), "Account1"));
  }

  private List<Operation> createOpTable(int size) {
    List<Operation> result = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Operation op = createOperation(i);

      result.add(op);
    }

    return result;
  }

  private Operation createOperation(int i) {
    Account a = new Account();
    a.setId((long) i);
    a.setVersion((short) 0);
    a.setName("Account" + i);

    Operation op = new Operation();
    op.setId((long) (i * 100));
    op.setVersion((short) 0);
    op.setType((i % 2 == 0) ? Type.CREDIT : Type.DEBIT);
    op.setAccount(a);
    return op;
  }
}
