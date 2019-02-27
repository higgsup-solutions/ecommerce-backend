package com.higgsup.xshop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
@Table(name = "CART")
@Data
public class Cart {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
  private Product product;

  @Column(name = "AMOUNT")
  private Integer amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  private User user;

  @Column(name = "STATUS")
  private String status;

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
