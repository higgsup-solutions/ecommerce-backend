package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;

import java.util.List;

public interface IOrderService {

    IPagedResponse<List<OrderDTO>> getListOrderByUserId(OrderCriteriaDTO criteria, int pageSize, int pageIndex);
}
