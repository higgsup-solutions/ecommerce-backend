package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.entity.Category;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.service.IProductService;
import org.springframework.beans.BeanUtils;
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
    Category category = categoryService.getCategoryById(id);

    List<Product> productList = productRepository.findProductsByCategory(category);

    if(CollectionUtils.isEmpty(productList)){
      for (Product product: productList) {
        productDTOList.add(convertProductEntityToDTO(product));
      }
    }
    return productDTOList;
  }

  private ProductDTO convertProductEntityToDTO(Product product){
    ProductDTO productDTO = new ProductDTO();
    BeanUtils.copyProperties(product, productDTO);
    return productDTO;
  }
}
