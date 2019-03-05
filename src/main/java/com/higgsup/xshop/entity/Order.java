package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.OrderStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "ORDER")
@Data
public class Order {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private OrderStatus status;

  @Column(name = "TOTAL_AMOUNT")
  private BigDecimal totalAmount;

  @Column(name = "DISCOUNT_AMOUNT")
  private BigDecimal discountAmount;

  @Column(name = "SHIPPING_FEE")
  private BigDecimal shippingFee;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Version
  private Integer version;
}
