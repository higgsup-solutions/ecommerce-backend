package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICategoryService;
import com.higgsup.xshop.service.IProductService;
import com.higgsup.xshop.service.impl.ProductService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="ProductController", description="Product Controller")
public class ProductController {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ProductService productService;

  public ProductController(
      ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/product/{id}")
  public ResponseMessage getProductByCategoryId (
      @PathVariable("id") int id) {
    ResponseMessage<List<ProductDTO>> response = new ResponseMessage<>();

    List<ProductDTO> productDTOList = productService.getProductByCategoryId(id);

    response.setData(productDTOList);

   return response;
  }
}
