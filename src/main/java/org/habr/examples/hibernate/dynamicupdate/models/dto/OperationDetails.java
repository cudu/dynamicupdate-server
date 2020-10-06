package org.habr.examples.hibernate.dynamicupdate.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDetails implements DetailsDTO<Operation> {
  private Long id;
  private short version;
  private int val;
  protected Type type;
  private AccountDetails account;

  @Override
  public Operation create() {
    return new Operation();
  }
}
