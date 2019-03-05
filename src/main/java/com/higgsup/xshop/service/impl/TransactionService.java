package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.common.WebUtil;
import com.higgsup.xshop.dto.TransactionDTO;
import com.higgsup.xshop.entity.Cart;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.entity.Transaction;
import com.higgsup.xshop.repository.CartRepository;
import com.higgsup.xshop.repository.ProductRepository;
import com.higgsup.xshop.repository.TransactionRepository;
import com.higgsup.xshop.repository.UserRepository;
import com.higgsup.xshop.security.model.UserContext;
import com.higgsup.xshop.service.ITransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

  private TransactionRepository transactionRepository;

  private UserRepository userRepository;

  private ProductRepository productRepository;

  private CartRepository cartRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      UserRepository userRepository,
      ProductRepository productRepository,
      CartRepository cartRepository) {
    this.transactionRepository = transactionRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
  }

  @Override
  public void createTransaction(TransactionDTO transactionDTO) {
    Transaction transaction = new Transaction();

    UserContext userContext = WebUtil.getCurrentUser();

    Integer userId = userContext.getUserId();

    BeanUtils.copyProperties(transactionDTO, transaction);
    transactionRepository.save(transaction);

    List<Cart> carts = cartRepository.findByUserId(userId);

    for (Cart cart : carts){
      Optional<Product> product = productRepository.findById(cart.getProduct().getId());
      Integer currentItem = product.get().getTempItem();
      Integer remainingItem = currentItem - cart.getAmount();

      product.get().setAvailableItem(remainingItem);
      productRepository.save(product.get());
    }
    cartRepository.deleteCartsByUserId(userId);
  }
}
