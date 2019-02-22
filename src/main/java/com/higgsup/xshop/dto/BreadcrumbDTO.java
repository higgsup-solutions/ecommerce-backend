package com.higgsup.xshop.dto;


public class BreadcrumbDTO {

  private Integer id;

  private String name;

  private Integer parentId;

  private Short level;

  public BreadcrumbDTO(Integer id, String name, Integer parentId,
      Short level) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.level = level;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getParentId() {
    return parentId;
  }

  public Short getLevel() {
    return level;
  }
}
