package com.higgsup.xshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartDetailDTO {
  private Integer id;

  private Integer productId;

  private String productName;

  private Integer amount;

  private BigDecimal unitPrice;

  private Float discountPercent;

  private String supplierName;

}
