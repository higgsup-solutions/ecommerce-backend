package com.higgsup.xshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
  private Integer id;

  private String name;

  private BigDecimal unitPrice;

  private Float discountPercent;

  private String mailImgUrl;

  private Float avgRating;

  private Long totalRating;
}
