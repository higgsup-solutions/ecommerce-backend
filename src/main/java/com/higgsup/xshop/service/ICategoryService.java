package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.BreadcrumbDTO;

import java.util.List;

public interface ICategoryService {

  List<BreadcrumbDTO> getBreadcrumbByCategoryId(Integer categoryId);

}
