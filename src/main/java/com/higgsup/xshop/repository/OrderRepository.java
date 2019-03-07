package com.higgsup.xshop.repository;

import com.higgsup.xshop.common.OrderStatus;
import com.higgsup.xshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRespositoryCustom,
    JpaSpecificationExecutor<Order> {

    List<Order> findFirst5ByUserId(Integer userId);

    Page<Order> findAllByUserId(Integer userId, Pageable pageable);

  List<Order> findByStatus(OrderStatus status);
}
