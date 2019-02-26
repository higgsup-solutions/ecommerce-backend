package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>,
    JpaSpecificationExecutor<Product>, ProductRepositoryCustom {

  Page<Product> findAllByCategory_Id(Integer category_id, Pageable pageRequest);

  Page<Product> findProductsByIdIn(List<Integer> listIds, Pageable pageRequest);

  @Query(value="SELECT DISTINCT p FROM Product p INNER JOIN FETCH p.supplier sp WHERE p.id = :productId")
  Optional<Product> findProductById(@Param("productId") Integer productId);



}
