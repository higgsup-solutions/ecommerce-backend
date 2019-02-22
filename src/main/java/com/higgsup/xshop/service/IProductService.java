package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;

import java.util.List;

public interface IProductService {
  List<ProductDTO> getProductTopSale();

  IPagedResponse<List<ProductDTO>> searchProduct(ProductCriteriaDTO criteria,
      int pageSize, int pageIndex);

  List<SupplierFilterDTO> getSupplierBySearchProduct(
      ProductCriteriaDTO criteriaDTO);
}
