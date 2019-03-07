package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository
    extends JpaRepository<OrderDetail, Integer>, OrderDetailRepositoryCustom {
  List<OrderDetail> findByOrderId(Integer OrderId);

  List<OrderDetail> findByOrderIdIn(List<Integer> idsOrder);
}
