package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.OrderProductDTO;

import java.util.List;

public interface IOrderDetailService {

  List<OrderProductDTO> getListOrderProduct(Integer orderId);
}
