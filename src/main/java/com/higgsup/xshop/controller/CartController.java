package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.dto.CartDTO;
import com.higgsup.xshop.dto.CartDetailDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  @GetMapping(value = "/total-item")
  @ApiOperation(value = "API get total item of cart")
  public IPagedResponse<Integer> getTotalItemCart() {
    ResponseMessage<Integer> responseMessage = new ResponseMessage<>();
    responseMessage.setData(cartService.totalItemCart());
    return new IPagedResponse<>(responseMessage);
  }

  @PostMapping
  @ApiOperation(value = "API add product into cart", response = IPagedResponse.class)
  public IPagedResponse<Object> addProduct(@RequestBody CartAddDTO cartAddDTO) {
    cartService.addProduct(cartAddDTO);
    return new IPagedResponse<>(new ResponseMessage<>());
  }

  @GetMapping
  @ApiOperation(value = "API get info cart", response = IPagedResponse.class)
  public IPagedResponse<List<CartDetailDTO>> getCartDetail() {
    ResponseMessage<List<CartDetailDTO>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(cartService.getCartDetail());
    return new IPagedResponse<>(responseMessage);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "API delete product into cart", response = IPagedResponse.class)
  public IPagedResponse<Object> deleteProduct(@PathVariable("id") Integer id) {
    cartService.deleteProduct(id);
    return new IPagedResponse<>(new ResponseMessage<>());
  }
}
