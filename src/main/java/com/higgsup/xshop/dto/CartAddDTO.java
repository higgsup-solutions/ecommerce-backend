package com.higgsup.xshop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartAddDTO {

  @NotNull(message = "ProductId is required")
  private Integer productId;

  @NotNull(message = "Amount is required")
  @Min(1)
  private Integer amount;
}
