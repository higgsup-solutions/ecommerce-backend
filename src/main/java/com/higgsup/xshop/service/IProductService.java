package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.ProductDTO;

import java.util.List;

public interface IProductService {
  List<ProductDTO> getProductByCategoryId(Integer id);
}
