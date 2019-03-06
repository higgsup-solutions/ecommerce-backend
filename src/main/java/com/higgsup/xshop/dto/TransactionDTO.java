package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.PayPalTransactionStatus;
import lombok.Data;

@Data
public class TransactionDTO {
  private String paypalOrderId;
  private String paypalTransactionId;
  private PayPalTransactionStatus status;
  private Integer xshopOrderId;
}
