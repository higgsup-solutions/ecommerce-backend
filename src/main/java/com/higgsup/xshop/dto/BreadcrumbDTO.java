package com.higgsup.xshop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Data
@RequiredArgsConstructor
@Getter
public class BreadcrumbDTO {

  @NonNull
  private Integer id;
  @NonNull
  private String name;
  @NonNull
  private BigInteger parentId;
  @NonNull
  private Short level;



}
