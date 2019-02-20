package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  Page<Product> findProductsByCategory(Category category, Pageable pageRequest);
}
