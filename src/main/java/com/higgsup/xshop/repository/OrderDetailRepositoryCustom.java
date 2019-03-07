package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.OrderProductDTO;

import java.util.List;

public interface OrderDetailRepositoryCustom {

  List<OrderProductDTO> findProductOrderByOrderId(Integer orderId);
}
