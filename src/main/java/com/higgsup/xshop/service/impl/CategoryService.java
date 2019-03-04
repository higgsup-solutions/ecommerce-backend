package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.DataUtil;
import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.CategoryRepository;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.service.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

  private final CategoryRepository categoryRepository;

  private final ProductRepository productRepository;

  public CategoryService(CategoryRepository categoryRepository,
      ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
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
  public List<BreadcrumbDTO> getBreadcrumbByProductId(
      Integer productId) {

    List<BreadcrumbDTO> result;

    Optional<Product> productOptional = this.productRepository
        .findById(productId);

    Product product = productOptional
        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,
            ErrorCode.PRODUCT_NOT_FOUND.getErrorMessage()));

    try {
      result = this.categoryRepository
          .searchBreadcrumbByCategoryId(product.getCategory().getId());
    } catch (Exception e) {
      throw new BusinessException(ErrorCode.GLOBAL, "System error");
    }
    return result;
  }

  @Override
  public List<Integer> getListChildCategory(Integer categoryId) {
    return this.categoryRepository.getListChildCategory(categoryId);
  }

  @Override
  @Transactional(readOnly = true)
  public IPagedResponse<List<ProductDTO>> getListProductsByCategoryId(
      Integer categoryId, int pageSize, int pageIndex) {

    List<ProductDTO> productDTOs = new ArrayList<>();

    Page<Product> productPage;

    ResponseMessage<List<ProductDTO>> responseMessage = new ResponseMessage<>();
    IPagedResponse<List<ProductDTO>> iPagedResponse = new IPagedResponse<>(
        responseMessage);

    List<Integer> listChildCategoryId = this.categoryRepository
        .getListChildCategory(categoryId);
    Pageable pageRequest = PageRequest
        .of(pageIndex, pageSize, Sort.Direction.ASC, "unitPrice");

    listChildCategoryId.add(categoryId);

    productPage = this.productRepository
        .findProductsByIdIn(listChildCategoryId, pageRequest);

    if (!CollectionUtils.isEmpty(productPage.getContent())) {
      productPage.getContent()
          .forEach(product -> productDTOs.add(DataUtil.mapProductDTO(product)));
    }

    responseMessage.setData(productDTOs);
    iPagedResponse.setPageIndex(pageIndex);
    iPagedResponse.setPageSize(pageSize);
    iPagedResponse.setTotalItem(productPage.getTotalElements());
    iPagedResponse.setTotalPage(productPage.getTotalPages());

    return iPagedResponse;
  }

}

