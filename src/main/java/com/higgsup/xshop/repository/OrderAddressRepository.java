package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository
    extends JpaRepository<OrderAddress, Integer> {
}
