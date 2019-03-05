package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.PayPalTransactionStatus;
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
@Table(name = "ORDER_DETAIL")
@Data
public class OrderDetail {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
  private Product product;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private PayPalTransactionStatus status;

  @Column(name = "DELIVERY_TIME")
  private Timestamp deliveryTime;

  @Column(name = "DISCOUNT_PRICE")
  private BigDecimal discountPrice;

  @Column(name = "DISCOUNT_PERCENT")
  private Float discountPercent;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Version
  private Integer version;
}
