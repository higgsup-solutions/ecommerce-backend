package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.common.DataUtil;
import com.higgsup.xshop.common.JpaResultConverter;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.dto.SupplierFilterDTO;
import com.higgsup.xshop.repository.SupplierRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SupplierRepositoryCustomImpl implements SupplierRepositoryCustom {


  private final EntityManager entityManager;
  public SupplierRepositoryCustomImpl(
      EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional(readOnly = true)
  public List<SupplierFilterDTO> getDistinctSupplierByCriteria(
      ProductCriteriaDTO criteria) {
    StringBuilder sql = new StringBuilder(
        "select s.id, s.name from supplier s ");
    sql.append("where id in ( ");
    sql.append(" select distinct supplier_id from product where 1 = 1 ");
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      sql.append(
          " and match(name, short_desc, full_desc) against(:textSearch in boolean mode) ");
    }
    if (criteria.getAvgRating() != null) {
      sql.append(" and avg_rating >= :avgRating ");
    }
    if (criteria.getStatus() != null) {
      sql.append(" and status = :status ");
    }
    if (criteria.getFromUnitPrice() != null) {
      sql.append(" and unit_price >= :fromUnitPrice ");
    }
    if (criteria.getToUnitPrice() != null) {
      sql.append(" and unit_price <= :toUnitPrice ");
    }
    sql.append(" ) ");

    Query query = entityManager.createNativeQuery(sql.toString());
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      String textSearch = DataUtil.removeAccent(criteria.getTextSearch())
          .toLowerCase();
      String textSearch1 = StringUtils.replace(textSearch, "d", "đ");
      String textSearch2 = StringUtils.replace(textSearch, "đ", "d");
      query.setParameter("textSearch", textSearch1 + " " + textSearch2);
    }
    if (criteria.getAvgRating() != null) {
      query.setParameter("avgRating", criteria.getAvgRating());
    }
    if (criteria.getStatus() != null) {
      query.setParameter("status", criteria.getStatus().name());
    }
    if (criteria.getFromUnitPrice() != null) {
      query.setParameter("fromUnitPrice", criteria.getFromUnitPrice());
    }
    if (criteria.getToUnitPrice() != null) {
      query.setParameter("toUnitPrice", criteria.getToUnitPrice());
    }

    return new JpaResultConverter().list(query, SupplierFilterDTO.class);
  }

  @Override
  public List<SupplierFilterDTO> getDistinctSupplierByCategory(
      List<Integer> categories) {

    StringBuilder sql = new StringBuilder(
        "select s.id, s.name from supplier s ");
    sql.append("where id in ( ");
    sql.append(
        " select distinct supplier_id from product where category_id in :categories ) ");

    Query query = entityManager.createNativeQuery(sql.toString());
    query.setParameter("categories", categories);

    return new JpaResultConverter().list(query, SupplierFilterDTO.class);
  }
}
