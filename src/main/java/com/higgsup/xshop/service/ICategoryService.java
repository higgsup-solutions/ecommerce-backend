package com.higgsup.xshop.service;

import com.higgsup.xshop.entity.Category;

import java.util.List;

public interface ICategoryService {
  Category getCategoryById(Integer id);
  List<Category> getCategoryByParentId(Integer id);
  List<Category> getAll();
}
