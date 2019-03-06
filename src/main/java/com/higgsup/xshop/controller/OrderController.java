package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ListOrderType;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.entity.OrderDetail;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public OrderController() {
    }

    @GetMapping()
    @ApiOperation(value = "api get list orders of user", response = IPagedResponse.class)
    public IPagedResponse<Order> getListOrder(
            @RequestParam(value = "type") ListOrderType type,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {



        return new IPagedResponse<>();
    }

    @GetMapping("/{id}")
    public IPagedResponse<OrderDetail> getOrderDetail() {


    }


}
