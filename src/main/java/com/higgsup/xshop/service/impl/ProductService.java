package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService implements IProductService {

  private ProductRepository productRepository;

  private CategoryService categoryService;

  public ProductService(
      ProductRepository productRepository,
      CategoryService categoryService) {
    this.productRepository = productRepository;
    this.categoryService = categoryService;
  }

  @Override
  public List<ProductDTO> getProductTopSale() {
    List<ProductDTO> productDTOs = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 18, Sort.Direction.DESC, "discountPercent");
    Page<Product> products = productRepository.findAll(pageRequest);
    products.getContent().forEach(product -> {
      ProductDTO productDTO = new ProductDTO();
      BeanUtils.copyProperties(product, productDTO);
      String[] imgUrls = product.getImgUrl().split(";");
      productDTO.setMailImgUrl(imgUrls[0]);
      productDTOs.add(productDTO);
    });
    return productDTOs;
  }
  @Override
  public List<ProductDTO> getProductByCategoryId(Integer id) {
    List<ProductDTO> productDTOList = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 18, Sort.Direction.DESC, "discountPercent");
    List<Category> allCategoryList = categoryService.getAll();
    Category categoryL1 = categoryService.getCategoryById(id);
    List<Category> categoryListL2 = categoryService.getCategoryByParentId(categoryL1.getId());
    List<Category> categoryListL3 = new ArrayList<>();

    for (Category categoryL2 : categoryListL2) {
      for (Category categoryL3 : allCategoryList) {
        if (categoryL3.getParentId().equals(categoryL2.getId())){
          categoryListL3.add(categoryL3);
        }
      }
    }

    List<Category> CategoryList = new ArrayList<>();

    CategoryList.add(categoryL1);
    CategoryList.addAll(categoryListL3);
    CategoryList.addAll(categoryListL2);

    Page<Product> productList = productRepository
        .findProductsByCategory(categoryL1, pageRequest);

    for (Product product : productList.getContent()) {
      ProductDTO productDTO = new ProductDTO();
      BeanUtils.copyProperties(product, productDTO);
      String[] imgUrls = product.getImgUrl().split(";");
      productDTO.setMailImgUrl(imgUrls[0]);
      productDTOList.add(productDTO);
    }

    return productDTOList;
  }

}
