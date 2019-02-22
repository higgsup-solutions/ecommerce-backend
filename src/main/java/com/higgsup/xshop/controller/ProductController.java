package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ProductStatus;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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
    ResponseMessage<List<ProductDTO>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(productService.getProductTopSale());
    return new IPagedResponse<>(responseMessage);
  }

  @GetMapping
  @ApiOperation(value = "API get search product", response = IPagedResponse.class)
  public IPagedResponse<List<ProductDTO>> searchProduct(
      @RequestParam(value = "textSearch", required = false) String textSearch,
      @RequestParam(value = "supplierId", required = false) Integer supplierId,
      @RequestParam(value = "fromUnitPrice", required = false) BigDecimal fromUnitPrice,
      @RequestParam(value = "toUnitPrice", required = false) BigDecimal toUnitPrice,
      @RequestParam(value = "avgRating", required = false) Integer avgRating,
      @RequestParam(value = "status", required = false) ProductStatus status,
      @RequestParam("pageIndex") Integer pageIndex,
      @RequestParam("pageSize") Integer pageSize
  ) {
    ProductCriteriaDTO criteria = new ProductCriteriaDTO();
    criteria.setTextSearch(textSearch);
    criteria.setSupplierId(supplierId);
    criteria.setFromUnitPrice(fromUnitPrice);
    criteria.setToUnitPrice(toUnitPrice);
    criteria.setAvgRating(avgRating);
    criteria.setStatus(status);

    return productService.searchProduct(criteria, pageSize, pageIndex);
  }

  @GetMapping("/supplier")
  @ApiOperation(value = "API get search product", response = IPagedResponse.class)
  public IPagedResponse<List<SupplierFilterDTO>> getSupplierWhenSearchProduct(
      @RequestParam(value = "textSearch", required = false) String textSearch,
      @RequestParam(value = "supplierId", required = false) Integer supplierId,
      @RequestParam(value = "fromUnitPrice", required = false) BigDecimal fromUnitPrice,
      @RequestParam(value = "toUnitPrice", required = false) BigDecimal toUnitPrice,
      @RequestParam(value = "avgRating", required = false) Integer avgRating,
      @RequestParam(value = "status", required = false) ProductStatus status
  ) {
    ProductCriteriaDTO criteria = new ProductCriteriaDTO();
    criteria.setTextSearch(textSearch);
    criteria.setSupplierId(supplierId);
    criteria.setFromUnitPrice(fromUnitPrice);
    criteria.setToUnitPrice(toUnitPrice);
    criteria.setAvgRating(avgRating);
    criteria.setStatus(status);

    ResponseMessage<List<SupplierFilterDTO>> responseMessage = new ResponseMessage<>();
    responseMessage
        .setData(productService.getSupplierBySearchProduct(criteria));

    return new IPagedResponse<>(responseMessage);
  }
}
