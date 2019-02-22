package com.higgsup.xshop.common;

public enum Number {
  NUMBER_OF_RELATED_PRODUCT (5);

  private Integer value;

  Number(Integer value) {
    this.value = value;
  }

  public Integer getValue() {
    return value;
  }
}
