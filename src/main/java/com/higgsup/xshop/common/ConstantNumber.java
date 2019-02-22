package com.higgsup.xshop.common;

public enum ConstantNumber {
  PAGE_INDEX(0),
  PAGE_SIZE_FOR_FEEDBACK(5),
  NUMBER_OF_RELATED_PRODUCT (5);

  private Integer value;

  ConstantNumber(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
