package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.ProductDTO;
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

  private final ProductRepository productRepository;

  public ProductService(
      ProductRepository productRepository) {
    this.productRepository = productRepository;
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
}
