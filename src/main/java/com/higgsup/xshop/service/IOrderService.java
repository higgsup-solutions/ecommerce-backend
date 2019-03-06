package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.entity.Order;

import java.util.List;

public interface IOrderService {

    IPagedResponse<List<Order>> getListOrderByUserId(OrderCriteriaDTO criteria, int pageSize, int pageIndex);
}
