package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailDTO {

  private Integer id;

  private String name;

  private String shortDesc;

  private String fullDesc;

  private String brandName;

  private ProductStatus status;

  private Float weight;

  private Integer availableItem;

  private BigDecimal unitPrice;

  private Float discountPercent;

  private Float avgRating;

  private Integer totalRating;

  private String imgUrl;

  private Integer categoryId;

  private Integer supplierId;

  private String supplierName;

  private String supplierAddress;

  private List<RatingDTO> ratingCount;
}
