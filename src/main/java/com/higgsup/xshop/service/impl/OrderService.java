package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ListOrderType;
import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.repository.OrderDetailRepository;
import com.higgsup.xshop.repository.OrderRepository;
import com.higgsup.xshop.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;

  private final OrderDetailRepository orderDetailRepository;

  public OrderService(OrderRepository orderRepository,
      OrderDetailRepository orderDetailRepository) {
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  @Override
  public IPagedResponse<List<OrderDTO>> getListOrderByUserId(
      OrderCriteriaDTO criteria, int pageSize, int pageIndex) {
    ResponseMessage<List<OrderDTO>> responseMessage = new ResponseMessage<>();
    IPagedResponse<List<OrderDTO>> iPagedResponse = new IPagedResponse<>(
        responseMessage);

    Pageable pageRequest = PageRequest
        .of(pageIndex, pageSize, Sort.Direction.ASC, "totalAmount");

    Integer userId = criteria.getUserId();
    ListOrderType orderType = criteria.getOrderListingType();

    switch (orderType) {
    case LAST_FIVE_ORDERS:
      List<OrderDTO> finalResult = new ArrayList<>();
      List<Order> listOrder = this.orderRepository.findFirst5ByUserId(userId);
      for (Order order : listOrder) {
        OrderDTO elm = new OrderDTO(order.getId(), order.getCreatedDate(),
            orderDetailRepository.findProductOrderByOrderId(order.getId()));
        finalResult.add(elm);
      }
      responseMessage.setData(finalResult);
      break;

    case ALL_ORDERS:
      List<OrderDTO> finalResult1 = new ArrayList<>();
      Page<Order> listOrder1 = this.orderRepository
          .findAllByUserId(userId, pageRequest);
      for (Order order : listOrder1) {
        OrderDTO elm = new OrderDTO(order.getId(), order.getCreatedDate(),
            orderDetailRepository.findProductOrderByOrderId(order.getId()));
        finalResult1.add(elm);
      }
      iPagedResponse.setTotalPage(listOrder1.getTotalPages());
      iPagedResponse.setTotalItem(listOrder1.getTotalElements());
      iPagedResponse.setPageSize(pageSize);
      iPagedResponse.setPageIndex(pageIndex);
      responseMessage.setData(finalResult1);
      break;

    case LAST_SIX_MONTHS:
      List<OrderDTO> finalResult2 = new ArrayList<>();
      Page<Order> listOrder2 = this.orderRepository
          .findListOrderLast6Months(userId, pageRequest);
      for (Order order : listOrder2.getContent()) {
        OrderDTO elm = new OrderDTO(order.getId(), order.getCreatedDate(),
            orderDetailRepository.findProductOrderByOrderId(order.getId()));
        finalResult2.add(elm);
      }
      iPagedResponse.setTotalPage(listOrder2.getTotalPages());
      iPagedResponse.setTotalItem(listOrder2.getTotalElements());
      iPagedResponse.setPageSize(pageSize);
      iPagedResponse.setPageIndex(pageIndex);
      responseMessage.setData(finalResult2);
      break;

    default:
      break;
    }

    return iPagedResponse;
  }
}
