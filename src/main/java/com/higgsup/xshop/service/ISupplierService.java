package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;

import java.util.List;

public interface ISupplierService {

  List<SupplierFilterDTO> getSupplierBySearchProduct(
      ProductCriteriaDTO criteria);
}
