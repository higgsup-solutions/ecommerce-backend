package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;

import java.util.List;

public interface ICategoryService {

  List<BreadcrumbDTO> getBreadcrumbByCategoryId(Integer categoryId);

  IPagedResponse<List<ProductDTO>> getListProductsByCategoryId(
      Integer categoryId,
      int pageSize, int pageIndex);

  List<BreadcrumbDTO> getBreadcrumbByProductId(Integer productId);

}
