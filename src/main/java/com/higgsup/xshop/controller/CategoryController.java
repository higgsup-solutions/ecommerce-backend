package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.ProductDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/categories")
@Api(value = "Category Controller", description = "controller related category")
public class CategoryController {

  private final ICategoryService categoryService;

  public CategoryController(
      ICategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/{id}/products")
  @ApiOperation(value = "API get category's products", response = IPagedResponse.class)
  public ResponseEntity<IPagedResponse<List<ProductDTO>>> getListProductsByCategoryId(
      @PathVariable("id") Integer categoryId, @RequestParam("pageIndex") Integer pageIndex,
      @RequestParam("pageSize") Integer pageSize) {

    IPagedResponse<List<ProductDTO>> result = this.categoryService.getListProductsByCategoryId(categoryId, pageSize, pageIndex);

    return ResponseEntity.ok(result);
  }
}
