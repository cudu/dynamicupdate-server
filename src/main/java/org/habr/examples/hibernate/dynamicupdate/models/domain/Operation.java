package org.habr.examples.hibernate.dynamicupdate.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@SuperBuilder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operations")
public class Operation extends AbstractDomainEntity {

  @Column
  @Enumerated(EnumType.STRING)
  private Type type;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "account_id")
  private Account account;

  @Column private int val;

  public enum Type {
    DEBIT,
    CREDIT
  }
}
