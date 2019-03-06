package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
  private Integer id;
  private Integer userId;
  private OrderStatus status;
  private BigDecimal totalAmount;
  private BigDecimal discountAmount;
  private BigDecimal shippingFee;
}
