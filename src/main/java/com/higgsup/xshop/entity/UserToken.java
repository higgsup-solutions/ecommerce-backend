package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.TokenType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
@Table(name="USER_TOKEN")
@Data
public class UserToken {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "USER_ID")
  private Integer userId;

  @Column(name = "TOKEN", columnDefinition = "TEXT")
  private String token;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private TokenType type;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Column(name = "VERSION")
  @Version
  private Integer version;
}
