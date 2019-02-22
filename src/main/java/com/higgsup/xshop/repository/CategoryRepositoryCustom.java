package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.BreadcrumbDTO;

import java.util.List;

public interface CategoryRepositoryCustom {

  List<BreadcrumbDTO> searchBreadcrumbByCategoryId(Integer categoryId);
}
