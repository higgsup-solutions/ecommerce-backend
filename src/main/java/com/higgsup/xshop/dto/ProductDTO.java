package com.higgsup.xshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
  private Integer id;

  private String name;

  private BigDecimal unitPrice;

  private BigDecimal discountPrice;

  private Float discountPercent;

  private String mainImgUrl;

  private Float avgRating;

  private Integer totalRating;
}
