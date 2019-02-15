package com.higgsup.base.dto.base;

import lombok.Data;

@Data
public abstract class IPagedResponse<T> {
  private ResponseMessage<T> responseMessage;
  private int totalItem;
  private int pageIndex;
  private int pageSize;
}
