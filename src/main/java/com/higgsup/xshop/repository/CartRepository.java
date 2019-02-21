package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  Cart findByProductIdAndUserId(Integer productId, Integer userId);
}
