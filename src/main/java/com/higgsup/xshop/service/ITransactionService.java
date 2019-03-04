package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.TransactionDTO;

public interface ITransactionService {
  void createTransaction(TransactionDTO transactionDTO);
}
