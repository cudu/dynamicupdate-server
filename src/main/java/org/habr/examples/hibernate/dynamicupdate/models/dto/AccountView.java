package org.habr.examples.hibernate.dynamicupdate.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.habr.examples.hibernate.dynamicupdate.models.domain.Account;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountView implements DTO<Account> {
  private Long id;
  private short version;
  private String name;

  @Override
  public Account create() {
    return new Account();
  }
}
