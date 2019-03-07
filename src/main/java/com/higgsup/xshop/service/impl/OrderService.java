package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.entity.*;
import com.higgsup.xshop.repository.*;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

  private OrderRepository orderRepository;
  private OrderAddressRepository orderAddressRepository;
  private UserRepository userRepository;
  private CartRepository cartRepository;
  private ProductRepository productRepository;

  public OrderService(
      OrderRepository orderRepository,
      OrderAddressRepository orderAddressRepository,
      UserRepository userRepository,
      CartRepository cartRepository,
      ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.orderAddressRepository = orderAddressRepository;
    this.userRepository = userRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
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

    List<Cart> carts = cartRepository.findByUserId(userId);

    for (Cart cart : carts){
      Optional<Product> product = productRepository.findById(cart.getProduct().getId());

      Integer tempItem = product.get().getTempItem();
      product.get().setTempItem(tempItem - cart.getAmount());
      productRepository.save(product.get());
    }
    cartRepository.deleteCartsByUserId(userId);

    orderAddress.setOrderId(order.getId());
    orderAddress.setAddress(orderDTO.getAddress());
    orderAddress.setBuyerName(orderDTO.getBuyer_name());
    orderAddress.setPhone(orderDTO.getPhone());
    orderAddress.setType(orderDTO.getType());

    orderAddressRepository.save(orderAddress);







    return orderDTO;
  }
}
