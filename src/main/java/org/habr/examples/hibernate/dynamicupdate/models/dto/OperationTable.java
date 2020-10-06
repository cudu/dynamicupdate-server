package org.habr.examples.hibernate.dynamicupdate.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationTable implements TableDTO {
  private Long id;
  private int val;
  protected String type;
  private String accountName;

}
