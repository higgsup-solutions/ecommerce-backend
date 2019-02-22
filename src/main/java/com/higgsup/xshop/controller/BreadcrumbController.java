package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/breadcrumbs")
public class BreadcrumbController {

  private final ICategoryService categoryService;

  public BreadcrumbController(ICategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping()
  @ApiOperation(value = "API update cart", response = IPagedResponse.class)
  public ResponseEntity<IPagedResponse<List<BreadcrumbDTO>>> getBreadcrumb(
      @RequestParam Integer categoryId) {
    ResponseMessage<List<BreadcrumbDTO>> responseMessage = new ResponseMessage<>();
    responseMessage
        .setData(this.categoryService.getBreadcrumbByCategoryId(categoryId));
    return ResponseEntity.ok(new IPagedResponse<>(responseMessage));
  }
}
