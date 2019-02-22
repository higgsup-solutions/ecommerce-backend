package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;

import java.util.List;

public interface SupplierRepositoryCustom {

  List<SupplierFilterDTO> getDistinctSupplierByCriteria(
      ProductCriteriaDTO criteria);
}
