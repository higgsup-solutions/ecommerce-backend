package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.AddressType;
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
@Table(name = "ORDER_ADDRESS")
@Data
public class OrderAddress {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "ORDER_ID")
  private Integer orderId;

  @Column(name = "BUYER_NAME")
  private String buyerName;

  @Column(name = "PHONE")
  private String phone;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "TYPE")
  @Enumerated(EnumType.STRING)
  private AddressType type;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Version
  private Integer version;
}
