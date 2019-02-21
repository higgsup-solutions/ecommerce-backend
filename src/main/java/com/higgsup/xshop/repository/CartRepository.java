package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Cart;
import com.higgsup.xshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  Cart findByProductIdAndUserId(Integer productId, Integer userId);

  @Query(value = "select sum(amount) from Cart where user = :user ")
  Integer countItemCartByUserId(@Param("user") User user);
}
