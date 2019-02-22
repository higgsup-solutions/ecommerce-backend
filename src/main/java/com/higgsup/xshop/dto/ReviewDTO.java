package com.higgsup.xshop.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDTO {
  private Integer id;
  private String reviewer;
  private Short rating;
  private String comment;
  private Timestamp createdDate;
  private Timestamp updatedDate;
}
