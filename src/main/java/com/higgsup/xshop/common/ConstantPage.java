package com.higgsup.xshop.common;

public enum ConstantPage {
  PAGE_INDEX_FOR_FEEDBACK(0),
  PAGE_SIZE_FOR_FEEDBACK(5);

  private Integer value;

  ConstantPage(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
