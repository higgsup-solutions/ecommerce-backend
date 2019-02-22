package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>,
    JpaSpecificationExecutor<Product> {

  @Query(value = "SELECT * from product WHERE category_id = :id "
      + "ORDER BY RAND() LIMIT :number", nativeQuery = true)
  List<Product> findRelatedProductByCategoryId(@Param("id") Integer id,
      @Param("number") Integer number);
}
