package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.common.OrderStatus;
import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.TransactionDTO;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.entity.OrderDetail;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.entity.Transaction;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.CartRepository;
import com.higgsup.xshop.repository.OrderDetailRepository;
import com.higgsup.xshop.repository.OrderRepository;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.repository.TransactionRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.ITransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

  private final TransactionRepository transactionRepository;

  private final UserRepository userRepository;

  private final ProductRepository productRepository;

  private final CartRepository cartRepository;

  private final OrderRepository orderRepository;

  private final OrderDetailRepository orderDetailRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      UserRepository userRepository,
      ProductRepository productRepository,
      CartRepository cartRepository,
      OrderRepository orderRepository,
      OrderDetailRepository orderDetailRepository) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.orderRepository = orderRepository;
    this.orderDetailRepository = orderDetailRepository;
  }

  @Override
  @Transactional
  public void createTransaction(TransactionDTO transactionDTO) {

    UserContext userContext = WebUtil.getCurrentUser();

    Integer userId = userContext.getUserId();
    Optional<Order> orderOptional = orderRepository
        .findById(transactionDTO.getXshopOrderId());

    Order order = orderOptional.orElseThrow(
        () -> new BusinessException(ErrorCode.ORDER_NOT_FOUND,
            ErrorCode.ORDER_NOT_FOUND.getErrorMessage()));
    if (!order.getUser().getId().equals(userId)) {
      throw new BusinessException(ErrorCode.UNAUTHORIZED,
          ErrorCode.UNAUTHORIZED.getErrorMessage());
    }

    order.setStatus(OrderStatus.PROCESS);

    orderRepository.save(order);

    List<OrderDetail> orderDetails = orderDetailRepository
        .findByOrderId(order.getId());

    List<Product> products = new ArrayList<>();
    orderDetails.forEach(orderDetail -> {
      orderDetail.setStatus(OrderStatus.PROCESS);
      Product product = orderDetail.getProduct();
      product.setAvailableItem(
          product.getAvailableItem() - orderDetail.getQuantity());
      products.add(product);
    });

    orderDetailRepository.saveAll(orderDetails);

    productRepository.saveAll(products);

    Transaction transaction = new Transaction();
    BeanUtils.copyProperties(transactionDTO, transaction);
    transactionRepository.save(transaction);

  }
}
