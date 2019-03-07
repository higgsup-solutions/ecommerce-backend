package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.OrderStatus;
import com.higgsup.xshop.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserOrderDTO {

  private Integer id;

  private OrderStatus status;

  private BigDecimal totalAmount;

  private BigDecimal discountAmount;

  private BigDecimal shippingFee;

  private Date createdDate;

  private List<OrderProductDTO> listOrderProduct;

}
