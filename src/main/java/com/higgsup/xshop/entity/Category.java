package com.higgsup.xshop.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
@Table(name = "CATEGORY")
@Data
public class Category {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "PARENT_ID")
  private Integer parentId;

  @Column(name = "LEVEL")
  private Short level;

  @Column(name = "SHORT_DESC", columnDefinition = "TEXT")
  private String shortDesc;

  @Column(name = "FULL_DESC", columnDefinition = "TEXT")
  private String fullDesc;

  @Column(name = "URI", columnDefinition = "TEXT")
  private String uri;

  @Column(name = "IMG_URL", columnDefinition = "TEXT")
  private String imgUrl;

  @Column(name = "CREATED_DATE")
  @CreationTimestamp
  private Timestamp createdDate;

  @Column(name = "UPDATED_DATE")
  @CreationTimestamp
  private Timestamp updatedDate;

  @Version
  private Integer version;

}
