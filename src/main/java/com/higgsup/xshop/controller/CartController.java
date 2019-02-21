package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.service.ICartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {

  private final ICartService cartService;

  public CartController(ICartService cartService) {
    this.cartService = cartService;
  }

  @PostMapping
  @ApiOperation(value = "API add product into cart", response = IPagedResponse.class)
  public IPagedResponse addProduct(@RequestBody CartAddDTO cartAddDTO) {
    cartService.addProduct(cartAddDTO);
    return new IPagedResponse();
  }
}
