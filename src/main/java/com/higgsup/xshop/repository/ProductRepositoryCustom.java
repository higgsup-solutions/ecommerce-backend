package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {

  Page<Product> searchByCriteria(ProductCriteriaDTO criteria,
      int pageIndex, int pageSize);
}
