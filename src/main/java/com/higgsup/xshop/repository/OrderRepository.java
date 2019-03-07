package com.higgsup.xshop.repository;

import com.higgsup.xshop.common.OrderStatus;
import com.higgsup.xshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  List<Order> findByStatus(OrderStatus status);
}
