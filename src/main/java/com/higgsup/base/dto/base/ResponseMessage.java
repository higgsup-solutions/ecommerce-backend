package com.higgsup.base.dto.base;

import lombok.Data;

@Data
public class ResponseMessage<T> {
  private String status;
  private String messageCode;
  private String messageString;
  private T data;
}
