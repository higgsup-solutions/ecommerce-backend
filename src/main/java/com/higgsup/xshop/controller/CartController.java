package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.CartDTO;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/carts")
@Api(value = "CartController", description = "Set of methods related to carts")
public class CartController {
  private final ICartService cartService;

  public CartController(ICartService cartService) {
    this.cartService = cartService;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  @ApiOperation(value = "API update cart")
  public ResponseMessage<CartDTO> updateCart (@PathVariable Integer id, @RequestBody CartDTO cartDTO){
    ResponseMessage<CartDTO> responseMessage = new ResponseMessage<>();
    CartDTO responseCartDTO = cartService.updateCart(id, cartDTO.getAmount());
    responseMessage.setData(responseCartDTO);
    return responseMessage;
  }
}
