package com.higgsup.xshop.dto;

import lombok.Data;

@Data
public class CategoryDTO {
  private Integer id;
  private String name;
  private Integer parentId;
  private Short level;
  private String shortDesc;
  private String fullDesc;
  private String uri;
  private String imgUrl;
}
