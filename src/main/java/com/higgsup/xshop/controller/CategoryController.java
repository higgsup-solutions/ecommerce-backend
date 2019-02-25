package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICategoryService;
import com.higgsup.xshop.service.ISupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@Api(value = "Category Controller", description = "controller related category")
public class CategoryController {

  private final ICategoryService categoryService;

  private final ISupplierService supplierService;

  public CategoryController(
      ICategoryService categoryService,
      ISupplierService supplierService) {
    this.categoryService = categoryService;
    this.supplierService = supplierService;
  }

  @GetMapping(value = "/{id}/products")
  @ApiOperation(value = "API get category's products", response = IPagedResponse.class)
  public ResponseEntity<IPagedResponse<List<ProductDTO>>> getListProductsByCategoryId(
      @PathVariable("id") Integer categoryId, @RequestParam("pageIndex") Integer pageIndex,
      @RequestParam("pageSize") Integer pageSize) {

    IPagedResponse<List<ProductDTO>> result = this.categoryService.getListProductsByCategoryId(categoryId, pageSize, pageIndex);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}/suppliers")
  @ApiOperation(value = "API get supplier search by category", response = IPagedResponse.class)
  public IPagedResponse<List<SupplierFilterDTO>> getSupplierByCategory(
      @PathVariable("id") Integer category
  ) {
    ResponseMessage<List<SupplierFilterDTO>> responseMessage = new ResponseMessage<>();

    responseMessage
        .setData(supplierService.getSupplierByCategory(category));

    return new IPagedResponse<>(responseMessage);
  }
}
