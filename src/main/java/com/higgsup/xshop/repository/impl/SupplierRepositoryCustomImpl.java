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
      sql.append("and (");
      sql.append("  ( upper(name) like :textSearch1 ");
      sql.append("    or upper(short_desc) like :textSearch1 ");
      sql.append("    or upper(full_desc) like :textSearch1 ");
      sql.append("  ) or ");
      sql.append("  ( upper(name) like :textSearch2 ");
      sql.append("    or upper(short_desc) like :textSearch2 ");
      sql.append("    or upper(full_desc) like :textSearch2 ");
      sql.append("  ) ");
      sql.append(" ) ");
    }
    if (criteria.getAvgRating() != null) {
      sql.append(" and avg_rating >= :avgRating ");
    }
    if (criteria.getStatus() != null) {
      sql.append(" and status = :status ");
    }
    if (criteria.getFromUnitPrice() != null
        && criteria.getToUnitPrice() != null) {
      sql.append(" unit_price >= :fromUnitPrice ");
      sql.append(" unit_price <= :toUnitPrice ");
    }
    sql.append(" ) ");

    Query query = entityManager.createNativeQuery(sql.toString());
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      String textSearch =
          "%" + DataUtil.removeAccent(criteria.getTextSearch()).toUpperCase()
              + "%";
      query.setParameter("textSearch1",
          StringUtils.replace(textSearch, "D", "Đ"));
      query.setParameter("textSearch2",
          StringUtils.replace(textSearch, "Đ", "D"));

    }
    if (criteria.getAvgRating() != null) {
      query.setParameter("avgRating", criteria.getAvgRating());
    }
    if (criteria.getStatus() != null) {
      query.setParameter("status", criteria.getStatus().name());
    }
    if (criteria.getFromUnitPrice() != null
        && criteria.getToUnitPrice() != null) {
      query.setParameter("fromUnitPrice", criteria.getFromUnitPrice());
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
