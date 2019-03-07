package com.higgsup.xshop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Getter
public class OrderProductDTO {

  private String name;

  private Integer quantity;

  private String status;

  private String imgUrl;
}
