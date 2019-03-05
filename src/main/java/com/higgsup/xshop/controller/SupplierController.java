package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ProductStatus;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICategoryService;
import com.higgsup.xshop.service.ISupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("api/suppliers")
@Api(value = "SupplierController", description = "Set of methods related to supplier")
public class SupplierController {

  private final ISupplierService supplierService;

  private final ICategoryService categoryService;

  public SupplierController(
      ISupplierService supplierService,
      ICategoryService categoryService) {
    this.supplierService = supplierService;
    this.categoryService = categoryService;
  }

  @GetMapping
  @ApiOperation(value = "API get supplier search by product", response = IPagedResponse.class)
  public IPagedResponse<List<SupplierFilterDTO>> getSupplierWhenSearchProduct(
      @RequestParam(value = "filterBy", required = false, defaultValue = "product") String filterBy,
      @RequestParam(value = "textSearch", required = false) String textSearch,
      @RequestParam(value = "supplierId", required = false) Integer supplierId,
      @RequestParam(value = "fromUnitPrice", required = false) BigDecimal fromUnitPrice,
      @RequestParam(value = "toUnitPrice", required = false) BigDecimal toUnitPrice,
      @RequestParam(value = "avgRating", required = false) Integer avgRating,
      @RequestParam(value = "categoryId", required = false) Integer categoryId,
      @RequestParam(value = "status", required = false) ProductStatus status
  ) {
    ProductCriteriaDTO criteria = new ProductCriteriaDTO();
    criteria.setTextSearch(textSearch);
    criteria.setSupplierId(supplierId);
    criteria.setFromUnitPrice(fromUnitPrice);
    criteria.setToUnitPrice(toUnitPrice);
    criteria.setAvgRating(avgRating);
    criteria.setStatus(status);

    if (categoryId != null) {
      List<Integer> listCategoryIds;
      listCategoryIds = this.categoryService
          .getListChildCategory(categoryId);
      listCategoryIds.add(categoryId);
      criteria.setCategoryIds(listCategoryIds);
    }

    ResponseMessage<List<SupplierFilterDTO>> responseMessage = new ResponseMessage<>();
    responseMessage
        .setData(supplierService.getSupplierBySearchProduct(criteria));

    return new IPagedResponse<>(responseMessage);
  }
}
