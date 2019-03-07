package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ListOrderType;
import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.dto.OrderListDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.entity.OrderDetail;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.IOrderService;
import com.higgsup.xshop.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final IOrderService orderService;

  private final IUserService userService;

  public OrderController(IOrderService orderService,
      IUserService userService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  @GetMapping()
  @ApiOperation(value = "api get list orders of user", response = IPagedResponse.class)
  public IPagedResponse<List<OrderListDTO>> getListOrder(
      @RequestParam(value = "type") ListOrderType type,
      @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
      @RequestParam(value = "pageSize", required = false) Integer pageSize) {

    UserContext userContext = WebUtil.getCurrentUser();

    OrderCriteriaDTO criteriaDTO = new OrderCriteriaDTO();
    criteriaDTO.setUserId(userContext.getUserId());
    criteriaDTO.setOrderListingType(type);

    return this.orderService
        .getListOrderByUserId(criteriaDTO, pageSize, pageIndex);
  }

  @GetMapping("/{id}")
  public IPagedResponse<OrderDetail> getOrderDetail() {

    return null;
  }
  @PostMapping
  @ApiOperation(value = "API create an order ", response = IPagedResponse.class)
  IPagedResponse<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
    orderService.createOrder(orderDTO);
    return new IPagedResponse<>(new ResponseMessage<>());
  }
}
