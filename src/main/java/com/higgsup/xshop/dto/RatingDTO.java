package com.higgsup.xshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@Getter
public class RatingDTO {

  private Short rating;

  private BigInteger counting;

}
