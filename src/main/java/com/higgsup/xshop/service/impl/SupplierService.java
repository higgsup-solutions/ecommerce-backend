package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.entity.Supplier;
import com.higgsup.xshop.repository.SupplierRepository;
import com.higgsup.xshop.service.ISupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService {

  private final SupplierRepository supplierRepository;

  public SupplierService(
      SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<SupplierFilterDTO> getSupplierBySearchProduct(
      ProductCriteriaDTO criteria) {

    List<SupplierFilterDTO> suppliers = new ArrayList<>();
    if (criteria.getSupplierId() != null) {
      Optional<Supplier> supplier = supplierRepository
          .findById(criteria.getSupplierId());
      if (supplier.isPresent()) {
        suppliers.add(new SupplierFilterDTO(supplier.get().getId(),
            supplier.get().getName()));
      }
    } else {
      suppliers = supplierRepository.getDistinctSupplierByCriteria(criteria);
    }

    return suppliers;
  }

}
