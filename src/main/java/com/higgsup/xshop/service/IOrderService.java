package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.OrderDTO;

public interface IOrderService {
  void createOrder(OrderDTO orderDTO);

  void cancelOrderWaitPayment();
}
