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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
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
  public List<ProductDTO> getProductByCategoryId(Integer id) {
    List<ProductDTO> productDTOList = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 18, Sort.Direction.DESC, "discountPercent");
    Category category = categoryService.getCategoryById(id);
    Page<Product> productList = productRepository
        .findProductsByCategory(category, pageRequest);

      for (Product product : productList.getContent()) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        String[] imgUrls = product.getImgUrl().split(";");
        productDTO.setMainImgUrl(imgUrls[0]);
        productDTOList.add(productDTO);
      }

    return productDTOList;
  }

}
