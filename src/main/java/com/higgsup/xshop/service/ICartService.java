package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.dto.base.ResponseMessage;

public interface ICartService {
  ResponseMessage addProduct(CartAddDTO cartAddDTO);
}
