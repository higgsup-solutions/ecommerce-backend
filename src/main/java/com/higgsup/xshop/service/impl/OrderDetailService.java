package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.OrderProductDTO;
import com.higgsup.xshop.repository.OrderDetailRepository;
import com.higgsup.xshop.service.IOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {

  private final OrderDetailRepository orderDetailRepository;

  public OrderDetailService(
      OrderDetailRepository orderDetailRepository) {
    this.orderDetailRepository = orderDetailRepository;
  }

  @Override
  public List<OrderProductDTO> getListOrderProduct(Integer orderId) {
    return this.orderDetailRepository.findProductOrderByOrderId(orderId);
  }
}
