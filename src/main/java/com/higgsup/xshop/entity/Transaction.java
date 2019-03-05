package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.PayPalTransactionStatus;
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
@Table(name = "TRANSACTION")
@Data
public class Transaction {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "PAYPAL_ORDER_ID")
  private String paypalOrderId;

  @Column(name = "PAYPAL_TRANSACTION_ID")
  private String paypalTransactionId;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private PayPalTransactionStatus status;

  @Column(name = "XSHOP_ORDER_ID")
  private Integer xshopOrderId;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Version
  private Integer version;

}
