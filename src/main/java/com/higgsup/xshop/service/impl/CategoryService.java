package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.CategoryRepository;
import com.higgsup.xshop.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.repository.CategoryRepository;
import com.higgsup.xshop.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository){
    this.categoryRepository = categoryRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<BreadcrumbDTO> getBreadcrumbByCategoryId(Integer categoryId) {
    List<BreadcrumbDTO> result;
    try {
      result = this.categoryRepository.searchBreadcrumbByCategoryId(categoryId);
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.GLOBAL, "System error");
    }
    return result;
  }

  @Override
  public Category getCategoryById(Integer id) {
    return categoryRepository.findById(id).orElse(null);
  }

  @Override
  public List<Category> getCategoryByParentId(Integer id) {
    return categoryRepository.findByParentId(id);
  }

  @Override
  public List<Category> getAll() {
    return categoryRepository.findAll();
  }
}

