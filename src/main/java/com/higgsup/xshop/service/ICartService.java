package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.dto.CartDTO;

public interface ICartService {
  CartDTO updateCart(Integer id, Integer amount);

  void addProduct(CartAddDTO cartAddDTO);
}
