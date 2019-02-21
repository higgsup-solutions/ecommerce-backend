package com.higgsup.xshop.dto.base;

import lombok.Data;

@Data
public class IPagedResponse<T> {
  private ResponseMessage<T> responseMessage = new ResponseMessage<>();

  private long totalItem;
  private int pageIndex;
  private int pageSize;

  private int totalPage;
}
