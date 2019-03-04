package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.BreadcrumbDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.exception.BusinessException;
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
  @ApiOperation(value = "API get breadcrumbs", response = IPagedResponse.class)
  public ResponseEntity<IPagedResponse<List<BreadcrumbDTO>>> getBreadcrumb(
      @RequestParam(required = false, name = "categoryId") Integer categoryId,
      @RequestParam(required = false, name = "productId") Integer productId) {

    ResponseMessage<List<BreadcrumbDTO>> responseMessage = new ResponseMessage<>();

    if (productId != null) {
      responseMessage
          .setData(this.categoryService.getBreadcrumbByProductId(productId));
    } else {
      if (categoryId != null) {
        responseMessage
            .setData(
                this.categoryService.getBreadcrumbByCategoryId(categoryId));
      } else {
        throw new BusinessException(ErrorCode.VALIDATION,
            "Input parameters are not valid");
      }
    }
    return ResponseEntity.ok(new IPagedResponse<>(responseMessage));
  }
}
