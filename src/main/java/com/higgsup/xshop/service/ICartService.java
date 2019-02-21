package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.CartAddDTO;

public interface ICartService {
  void addProduct(CartAddDTO cartAddDTO);
}
