package com.higgsup.xshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDTO {
  private String orderId;
  private String transactionId;
  private String status;
  private BigDecimal amount;
}
