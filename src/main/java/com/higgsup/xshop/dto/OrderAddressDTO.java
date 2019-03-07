package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.AddressType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderAddressDTO {

  @NotNull(message = "Buyer name fee is required")
  private String buyerName;

  @NotNull(message = "Phone fee is required")
  private String phone;

  @NotNull(message = "Address fee is required")
  private String address;

  @NotNull(message = "Address type fee is required")
  private AddressType type;
}
