package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.entity.OrderAddress;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.repository.OrderAddressRepository;
import com.higgsup.xshop.repository.OrderRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService implements IOrderService {

  private OrderRepository orderRepository;
  private OrderAddressRepository orderAddressRepository;
  private UserRepository userRepository;

  public OrderService(
      OrderRepository orderRepository,
      OrderAddressRepository orderAddressRepository,
      UserRepository userRepository) {
    this.orderRepository = orderRepository;
    this.orderAddressRepository = orderAddressRepository;
    this.userRepository = userRepository;
  }

  @Override
  public OrderDTO createOrder(OrderDTO orderDTO) {
    Order order = new Order();
    OrderAddress orderAddress = new OrderAddress();
    UserContext userContext = WebUtil.getCurrentUser();

    Integer userId = userContext.getUserId();
    Optional<User> user = userRepository.findById(userId);

    BeanUtils.copyProperties(orderDTO, order);
    order.setUser(user.get());

    orderRepository.save(order);
    orderDTO.setId(order.getId());
    orderDTO.setUserId(userId);

    orderAddress.setOrderId(order.getId());
    orderAddress.setAddress(orderDTO.getAddress());
    orderAddress.setBuyerName(orderDTO.getBuyer_name());
    orderAddress.setPhone(orderDTO.getPhone());
    orderAddress.setType(orderDTO.getType());

    orderAddressRepository.save(orderAddress);







    return orderDTO;
  }
}
