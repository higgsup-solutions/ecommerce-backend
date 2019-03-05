package com.higgsup.xshop.dto;

import lombok.Data;

@Data
public class TransactionDTO {
  private String paypalOrderId;
  private String paypalTransactionId;
  private String status;
  private Integer xshopOrderId;
}
