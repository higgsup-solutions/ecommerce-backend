package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCriteriaDTO {
  private Integer supplierId;

  private String textSearch;

  private BigDecimal fromUnitPrice;

  private BigDecimal toUnitPrice;

  private Integer avgRating;

  private ProductStatus status;
}
