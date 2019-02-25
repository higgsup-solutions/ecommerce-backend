package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISupplierService {

  List<SupplierFilterDTO> getSupplierBySearchProduct(
      ProductCriteriaDTO criteria);

  @Transactional(readOnly = true)
  List<SupplierFilterDTO> getSupplierByCategory(Integer categoryId);

}
