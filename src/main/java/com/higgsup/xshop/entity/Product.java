package com.higgsup.xshop.entity;

import com.higgsup.xshop.common.ProductStatus;
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
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "SHORT_DESC", columnDefinition = "TEXT")
  private String shortDesc;

  @Column(name = "FULL_DESC", columnDefinition = "TEXT")
  private String fullDesc;

  @Column(name = "BRAND_NAME")
  private String brandName;

  @Column(name = "STATUS")
  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @Column(name = "WEIGHT")
  private Float weight;

  @Column(name = "AVAILABLE_ITEM")
  private Integer availableItem;

  @Column(name = "UNIT_PRICE")
  private BigDecimal unitPrice;

  @Column(name = "DISCOUNT_PERCENT")
  private Float discountPercent;

  @Column(name = "AVG_RATING")
  private Float avgRating;

  @Column(name = "TOTAL_RATING")
  private Integer totalRating;

  @Column(name = "IMG_URL", columnDefinition = "TEXT")
  private String imgUrl;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Column(name = "VERSION")
  private Integer version;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
  private Supplier supplier;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
  private Category category;

}
