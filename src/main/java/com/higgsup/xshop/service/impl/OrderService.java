package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ListOrderType;
import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.repository.OrderRepository;
import com.higgsup.xshop.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public IPagedResponse<List<Order>> getListOrderByUserId(OrderCriteriaDTO criteria, int pageSize, int pageIndex) {
        ResponseMessage<List<Order>> responseMessage = new ResponseMessage<>();
        IPagedResponse<List<Order>> iPagedResponse = new IPagedResponse<>(responseMessage);

        List<Order> result;

        Pageable pageRequest = PageRequest
                .of(pageIndex, pageSize, Sort.Direction.ASC, "totalAmount");

        Integer userId = criteria.getUserId();
        ListOrderType orderType = criteria.getOrderListingType();


        if (orderType.equals(ListOrderType.LAST_FIVE_ORDERS)) {
            result = this.orderRepository.findFirst5ByUserId(userId);
            responseMessage.setData(result);
        } else if (orderType.equals(ListOrderType.ALL_ORDERS)) {
            Page<Order> listOrders = this.orderRepository.findDistinctByUserId(userId, pageRequest);
        } else if (orderType.equals(ListOrderType.LAST_SIX_MONTHS)){

        }


            return iPagedResponse;
    }
}
