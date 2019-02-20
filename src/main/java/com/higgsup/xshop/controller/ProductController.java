package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Api(value = "ProductController", description = "Set of methods related to products")
public class ProductController {

  private final IProductService productService;

  public ProductController(
      IProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/top-sale")
  @ApiOperation(value = "API get top sale product", response = IPagedResponse.class)
  public IPagedResponse<List<ProductDTO>> getProductTopSale() {
    IPagedResponse<List<ProductDTO>> iPagedResponse = new IPagedResponse<>();
    ResponseMessage<List<ProductDTO>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(productService.getProductTopSale());
    iPagedResponse.setResponseMessage(responseMessage);
    return iPagedResponse;
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "API get products by category", response = IPagedResponse.class)
  public IPagedResponse getProductByCategoryId (
      @PathVariable("id") int id) {
    IPagedResponse<List<ProductDTO>> iPagedResponse = new IPagedResponse<>();
    ResponseMessage<List<ProductDTO>> responseMessage = new ResponseMessage<>();
    List<ProductDTO> productDTOList = productService.getProductByCategoryId(id);
    responseMessage.setData(productDTOList);
    iPagedResponse.setResponseMessage(responseMessage);
    iPagedResponse.setTotalItem(productDTOList.size());
    return iPagedResponse;
  }
}
