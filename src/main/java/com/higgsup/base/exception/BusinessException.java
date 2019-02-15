package com.higgsup.base.exception;

import com.higgsup.base.common.ErrorCode;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  private final String message;

  public BusinessException(ErrorCode errorCode, String message,
      Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
    this.message = message;
  }

  public BusinessException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.message = message;
  }

  public BusinessException(ErrorCode errorCode, String message,
      Object... parameter) {
    super(message);
    this.errorCode = errorCode;
    MessageFormat fmt = new MessageFormat(message);
    this.message = fmt.format(parameter);
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
