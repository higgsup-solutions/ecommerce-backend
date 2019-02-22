package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.entity.Category;

import java.util.List;

public interface ICategoryService {

  List<BreadcrumbDTO> getBreadcrumbByCategoryId(Integer categoryId);

  Category getCategoryById(Integer id);
  List<Category> getCategoryByParentId(Integer id);
  List<Category> getAll();
}
