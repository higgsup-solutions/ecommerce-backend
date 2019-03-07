package com.higgsup.xshop.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum ErrorCode {
  GLOBAL(2, "System error"),
  VALIDATION(1, "Validation error"),
  AUTHENTICATION(10, "Authentication error"),
  JWT_TOKEN_EXPIRED(11, "JWT token expired"),
  UNAUTHORIZED(12, "Unauthorized"),

  //Cart error
  AMOUNT_GREATER_THAN_AVAILABLE_PRODUCT(12,
      "Amount product greater than available product"),

  //Product error
  PRODUCT_NOT_FOUND(13, "Product not found"),

  //User error
  USER_EMAIL_EXISTED(14, "User email existed."),

  //ORDER ERROR
  ORDER_NOT_FOUND(15, "Order not found.");

  private int errorCode;

  private String errorMessage;

  ErrorCode(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  @JsonValue
  public int getErrorCode() {
    return this.errorCode;
  }

  @JsonValue
  public String getErrorMessage() {
    return this.errorMessage;
  }
}
