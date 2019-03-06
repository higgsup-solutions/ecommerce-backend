package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.OrderDTO;

public interface IOrderService {
  OrderDTO createOrder(OrderDTO orderDTO);
}
