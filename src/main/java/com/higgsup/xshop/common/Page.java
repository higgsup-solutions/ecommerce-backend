package com.higgsup.xshop.common;

public enum Page {
  PAGE_INDEX_FOR_FEEDBACK(0),
  PAGE_SIZE_FOR_FEEDBACK(5);

  private Integer value;

  Page(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
