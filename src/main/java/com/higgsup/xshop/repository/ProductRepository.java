package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  List<Product> findProductsByCategory(Category category);

}
