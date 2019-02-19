package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.UserRole;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
@Data
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "ROLE")
  private UserRole role;

  public String authority() {
    return "ROLE_" + this.role.name();
  }
}
