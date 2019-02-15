package com.higgsup.base.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER_TOKEN")
@Data
public class UserToken {

  @Id
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "TOKEN")
  private String token;

  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;

}
