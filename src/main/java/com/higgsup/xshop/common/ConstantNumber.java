package com.higgsup.xshop.common;

public enum ConstantNumber {
  NUMBER_OF_RELATED_PRODUCT (5);

  private Integer value;

  ConstantNumber(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
