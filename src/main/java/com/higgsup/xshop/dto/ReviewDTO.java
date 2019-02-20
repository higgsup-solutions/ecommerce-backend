package com.higgsup.xshop.dto;

import com.higgsup.xshop.entity.Product;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
  private Integer id;
  private String reviewer;
  private Short rating;
  private String comment;
  private Product product;
  private Timestamp createdDate;
  private Timestamp updatedDate;
}
