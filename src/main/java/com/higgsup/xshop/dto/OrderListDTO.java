package com.higgsup.xshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderListDTO {

  private Integer id;

  private Timestamp createDate;

  private List<OrderProductDTO> listProduct;

}