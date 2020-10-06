package org.habr.examples.hibernate.dynamicupdate.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation.Type;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Operation2;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operation2Details implements DetailsDTO<Operation2> {
  private Long id;
  private short version;
  private int val;
  protected Type type;
  private AccountDetails account;

  @Override
  public Operation2 create() {
    return new Operation2();
  }
}
