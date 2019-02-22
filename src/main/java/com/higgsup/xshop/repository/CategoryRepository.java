package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer>, CategoryRepositoryCustom  {

  List<Category> findByParentId(Integer id);
}
