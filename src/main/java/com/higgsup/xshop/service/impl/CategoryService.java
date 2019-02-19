package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.repository.CategoryRepository;
import com.higgsup.xshop.service.ICategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
  private CategoryRepository categoryRepository;

  public CategoryService(
      CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category getCategoryById(Integer id) {
    return categoryRepository.findById(id).orElse(null);
  }
}