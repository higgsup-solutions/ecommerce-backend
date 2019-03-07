package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.common.ListOrderType;
import com.higgsup.xshop.common.OrderStatus;
import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.OrderAddressDTO;
import com.higgsup.xshop.dto.OrderCriteriaDTO;
import com.higgsup.xshop.dto.OrderDTO;
import com.higgsup.xshop.dto.OrderListDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.Cart;
import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.entity.OrderAddress;
import com.higgsup.xshop.entity.OrderDetail;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.CartRepository;
import com.higgsup.xshop.repository.OrderAddressRepository;
import com.higgsup.xshop.repository.OrderDetailRepository;
import com.higgsup.xshop.repository.OrderRepository;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.higgsup.xshop.common.ApplicationConstant.TIME_CANCEL_ORDER;

@Service
public class OrderService implements IOrderService {

  private final OrderRepository orderRepository;

  private final OrderAddressRepository orderAddressRepository;

  private final UserRepository userRepository;

  private final CartRepository cartRepository;

  private final ProductRepository productRepository;

  private final OrderDetailRepository orderDetailRepository;

  private final ValidationService validationService;

  public OrderService(
      OrderRepository orderRepository,
      OrderAddressRepository orderAddressRepository,
      UserRepository userRepository,
      CartRepository cartRepository,
      ProductRepository productRepository,
      OrderDetailRepository orderDetailRepository,
      ValidationService validationService) {
    this.orderRepository = orderRepository;
    this.orderAddressRepository = orderAddressRepository;
    this.userRepository = userRepository;
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.orderDetailRepository = orderDetailRepository;
    this.validationService = validationService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createOrder(OrderDTO orderDTO) {
    UserContext userContext = WebUtil.getCurrentUser();
    Integer userId = userContext.getUserId();

    validateOrder(orderDTO);

    List<Cart> carts = cartRepository.findByUserId(userId);

    if (CollectionUtils.isEmpty(carts)) {
      return;
    }

    List<OrderDetail> orderDetails = new ArrayList<>();

    List<Product> products = new ArrayList<>();

    BigDecimal totalAmount = new BigDecimal(0);
    BigDecimal discountAmount = new BigDecimal(0);

    Order order = new Order();
    Optional<User> user = userRepository.findById(userId);
    order.setStatus(OrderStatus.WAIT_FOR_PAY);
    order.setUser(user.orElseThrow(
        () -> new BusinessException(ErrorCode.VALIDATION,
            ErrorCode.VALIDATION.getErrorMessage())));

    for (Cart cart : carts) {
      Product product = cart.getProduct();
      Integer tempItem = product.getTempItem();
      Integer quantity = cart.getAmount();

      if (tempItem < quantity) {
        throw new BusinessException(
            ErrorCode.AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT,
            ErrorCode.AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT.getErrorMessage());
      }
      product.setTempItem(tempItem - quantity);
      products.add(product);

      BigDecimal totalPrice = product.getUnitPrice()
          .multiply(BigDecimal.valueOf(quantity));
      BigDecimal discountPrice = product.getDiscountPrice()
          .multiply(BigDecimal.valueOf(quantity));
      totalAmount = totalAmount.add(totalPrice);
      discountAmount = discountAmount.add(discountPrice);

      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setStatus(OrderStatus.WAIT_FOR_PAY);
      orderDetail
          .setDeliveryTime(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));
      orderDetail.setDiscountPercent(product.getDiscountPercent());
      orderDetail.setQuantity(quantity);
      orderDetail.setUnitPrice(product.getUnitPrice());
      orderDetail.setProduct(product);
      orderDetail.setOrder(order);
      orderDetails.add(orderDetail);
    }

    order.setDiscountAmount(discountAmount);
    order.setShippingFee(orderDTO.getShippingFee());
    order.setTotalAmount(totalAmount);

    orderRepository.save(order);

    saveOrderAddress(orderDTO.getAddress(), order.getId());

    productRepository.saveAll(products);

    orderDetailRepository.saveAll(orderDetails);

    cartRepository.deleteAll(carts);

  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void cancelOrderWaitPayment() {
    List<Order> orders = orderRepository.findByStatus(OrderStatus.WAIT_FOR_PAY);
    if (CollectionUtils.isEmpty(orders)) {
      List<Order> ordersCancel = new ArrayList<>();
      List<Integer> idsOrder = new ArrayList<>();
      orders.forEach(order -> {
        Long currentTime = System.currentTimeMillis();
        long time = currentTime - order.getCreatedDate().getTime();
        if (time > TIME_CANCEL_ORDER) {
          order.setStatus(OrderStatus.CANCELED);
          ordersCancel.add(order);
          idsOrder.add(order.getId());
        }
      });

      if (CollectionUtils.isEmpty(idsOrder)) {
        return;
      }

      List<OrderDetail> orderDetailsCancel = orderDetailRepository
          .findByOrderIdIn(idsOrder);

      List<Product> products = new ArrayList<>();
      orderDetailsCancel.forEach(orderDetail -> {
        orderDetail.setStatus(OrderStatus.CANCELED);
        Product product = orderDetail.getProduct();
        Integer tempItem = product.getTempItem();
        product.setTempItem(tempItem + orderDetail.getQuantity());
        products.add(product);
      });

      productRepository.saveAll(products);

      orderDetailRepository.saveAll(orderDetailsCancel);

      orderRepository.saveAll(ordersCancel);

    }
  }

  private void saveOrderAddress(List<OrderAddressDTO> orderAddressDTOs,
      Integer orderId) {

    List<OrderAddress> orderAddressList = new ArrayList<>();
    orderAddressDTOs.forEach(orderAddressDTO -> {
      OrderAddress orderAddress = new OrderAddress();
      BeanUtils.copyProperties(orderAddressDTO, orderAddress);
      orderAddress.setOrderId(orderId);
      orderAddressList.add(orderAddress);
    });

    orderAddressRepository.saveAll(orderAddressList);
  }

  private void validateOrder(OrderDTO orderDTO) {
    if (validationService.validate(orderDTO)) {
      throw new BusinessException(ErrorCode.VALIDATION,
          ErrorCode.VALIDATION.getErrorMessage());
    }
  }

  @Override
  public IPagedResponse<List<OrderListDTO>> getListOrderByUserId(
      OrderCriteriaDTO criteria, int pageSize, int pageIndex) {
    ResponseMessage<List<OrderListDTO>> responseMessage = new ResponseMessage<>();
    IPagedResponse<List<OrderListDTO>> iPagedResponse = new IPagedResponse<>(
        responseMessage);

    Pageable pageRequest = PageRequest
        .of(pageIndex, pageSize, Sort.Direction.ASC, "totalAmount");

    Integer userId = criteria.getUserId();
    ListOrderType orderType = criteria.getOrderListingType();

    switch (orderType) {
    case LAST_FIVE_ORDERS:
      List<OrderListDTO> finalResult = new ArrayList<>();
      List<Order> listOrder = this.orderRepository.findFirst5ByUserId(userId);
      for (Order order : listOrder) {
        OrderListDTO elm = new OrderListDTO(order.getId(), order.getCreatedDate(),
            orderDetailRepository.findProductOrderByOrderId(order.getId()));
        finalResult.add(elm);
      }
      responseMessage.setData(finalResult);
      break;

    case ALL_ORDERS:
      List<OrderListDTO> finalResult1 = new ArrayList<>();
      Page<Order> listOrder1 = this.orderRepository
          .findAllByUserId(userId, pageRequest);
      for (Order order : listOrder1) {
        OrderListDTO elm = new OrderListDTO(order.getId(), order.getCreatedDate(),
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
      List<OrderListDTO> finalResult2 = new ArrayList<>();
      Page<Order> listOrder2 = this.orderRepository
          .findListOrderLast6Months(userId, pageRequest);
      for (Order order : listOrder2.getContent()) {
        OrderListDTO elm = new OrderListDTO(order.getId(), order.getCreatedDate(),
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
