package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  Optional<Cart> findById(Integer id);

  Cart findByProductIdAndUserId(Integer productId, Integer userId);
}
