package com.higgsup.xshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RelatedProductDTO {
  private Integer id;

  private String name;

  private String mainImgUrl;

  private BigDecimal unitPrice;
}
