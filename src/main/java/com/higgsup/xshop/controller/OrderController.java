package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
@Api(value = "OrderController", description = "Set of methods related to order")
public class OrderController {
  private final IOrderService orderService;

  public OrderController(IOrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ApiOperation(value = "API create an order ", response = IPagedResponse.class)
  IPagedResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
    orderService.createOrder(orderDTO);
    return new IPagedResponse<>(new ResponseMessage<>());
  }
}
