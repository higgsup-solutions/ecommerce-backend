package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.CartDTO;
import com.higgsup.xshop.entity.Cart;
import com.higgsup.xshop.repository.CartRepository;
import com.higgsup.xshop.service.ICartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {

  private final CartRepository cartRepository;

  public CartService(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public CartDTO updateCart(Integer id, Integer amount) {
    CartDTO cartDTO = new CartDTO();
    Optional<Cart> optionalCart = cartRepository.findById(id);

    if (optionalCart.isPresent()){
      Cart cart = optionalCart.get();
      cart.setAmount(amount);
      cartRepository.save(cart);
      BeanUtils.copyProperties(cart, cartDTO);
    }
    return cartDTO;
  }
}
