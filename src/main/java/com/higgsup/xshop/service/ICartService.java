package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.dto.CartDTO;
import com.higgsup.xshop.dto.CartDetailDTO;

import java.util.List;

public interface ICartService {
  CartDTO updateCart(Integer id, Integer amount);

  Integer totalItemCart();

  void addProduct(CartAddDTO cartAddDTO);

  List<CartDetailDTO> getCartDetail();


  void deleteProduct(Integer id);
}
