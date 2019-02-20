package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  Page<Product> findAll(Pageable pageRequest);
}
