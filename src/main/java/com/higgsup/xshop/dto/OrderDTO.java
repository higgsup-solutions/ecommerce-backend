package com.higgsup.xshop.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {

  @NotNull(message = "Shipping fee is required")
  private BigDecimal shippingFee;

  @NotEmpty(message = "Address is required")
  private List<OrderAddressDTO> address;
}
