package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.CartAddDTO;
import com.higgsup.xshop.entity.Cart;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.repository.CartRepository;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.ICartService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService implements ICartService {

  private final CartRepository cartRepository;

  private final ProductRepository productRepository;

  private final UserRepository userRepository;

  private final ValidationService validationService;

  public CartService(CartRepository cartRepository,
      ProductRepository productRepository,
      UserRepository userRepository,
      ValidationService validationService) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.validationService = validationService;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addProduct(CartAddDTO cartAddDTO) {

    UserContext userContext = (UserContext) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    validateProduct(cartAddDTO);

    Product product = productRepository.getOne(cartAddDTO.getProductId());
    if (product == null) {
      throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND,
          ErrorCode.PRODUCT_NOT_FOUND.getErrorMessage());
    }

    Cart cart = cartRepository
        .findByProductIdAndUserId(cartAddDTO.getProductId(),
            userContext.getUserId());
    if (cart == null) {
      cart = new Cart();
      BeanUtils.copyProperties(cartAddDTO, cart);
      User user = userRepository.getOne(userContext.getUserId());
      cart.setUser(user);
      cart.setAmount(cartAddDTO.getAmount());
    } else {
      cart.setProduct(product);
      cart.setAmount(cart.getAmount() + cartAddDTO.getAmount());
    }

    if (product.getAvailableItem() < cart.getAmount()) {
      throw new BusinessException(
          ErrorCode.AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT,
          ErrorCode.AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT.getErrorMessage());
    }

    cartRepository.save(cart);
  }

  private void validateProduct(CartAddDTO cartAddDTO) {
    if (!validationService.validate(cartAddDTO)) {
      throw new BusinessException(ErrorCode.VALIDATION,
          ErrorCode.VALIDATION.getErrorMessage());
    }
  }
}
