package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>,
    JpaSpecificationExecutor<Product> {

  Page<Product> findProductsByIdIn(List<Integer> listIds, Pageable pageRequest);

}
