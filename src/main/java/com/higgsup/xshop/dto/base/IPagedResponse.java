package com.higgsup.xshop.dto.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IPagedResponse<T> {

  private final ResponseMessage<T> responseMessage;

  private long totalItem;
  private int pageIndex;
  private int pageSize;

  private int totalPage;
}
