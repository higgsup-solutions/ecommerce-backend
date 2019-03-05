package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository
    extends JpaRepository<OrderDetail, Integer> {
}
