package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.common.DataUtil;
import com.higgsup.xshop.dto.ProductCriteriaDTO;
import com.higgsup.xshop.entity.Product;
import com.higgsup.xshop.repository.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

  private final EntityManager entityManager;

  public ProductRepositoryCustomImpl(
      EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Page<Product> searchByCriteria(ProductCriteriaDTO criteria,
      int pageIndex, int pageSize) {
    StringBuilder sqlData = new StringBuilder("select * from product ");
    StringBuilder sqlCount = new StringBuilder("select count(*) from product ");
    StringBuilder conditionWhere = buildCondition(criteria);

    Query queryData = entityManager.createNativeQuery(
        sqlData.append(conditionWhere).append(" order by discount_price ")
            .toString(), Product.class);
    setParameter(queryData, criteria);
    queryData.setFirstResult(pageSize * pageIndex + 1);
    queryData.setMaxResults(pageSize);

    List<Product> dataList = queryData.getResultList();

    Query queryCount = entityManager
        .createNativeQuery(sqlCount.append(conditionWhere).toString());
    setParameter(queryCount, criteria);

    BigInteger totalSize = (BigInteger) queryCount.getSingleResult();
    Pageable pageRequest = PageRequest
        .of(pageIndex, pageSize, Sort.Direction.ASC, "discountPrice");

    return new PageImpl<>(dataList, pageRequest, totalSize.longValue());

  }

  private void setParameter(Query query, ProductCriteriaDTO criteria) {
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      String textSearch = DataUtil.removeAccent(criteria.getTextSearch())
          .toUpperCase();
      String textSearch1 = StringUtils.replace(textSearch, "D", "Đ");
      String textSearch2 = StringUtils.replace(textSearch, "Đ", "D");
      query.setParameter("textSearch", textSearch1 + " " + textSearch2);
    }
    if (criteria.getAvgRating() != null) {
      query.setParameter("avgRating", criteria.getAvgRating());
    }
    if (criteria.getStatus() != null) {
      query.setParameter("status", criteria.getStatus().name());
    }
    if (criteria.getSupplierId() != null) {
      query.setParameter("supplierId", criteria.getSupplierId());
    }
    if (criteria.getFromUnitPrice() != null) {
      query.setParameter("fromUnitPrice", criteria.getFromUnitPrice());
    }
    if (criteria.getToUnitPrice() != null) {
      query.setParameter("toUnitPrice", criteria.getToUnitPrice());
    }
  }

  private StringBuilder buildCondition(ProductCriteriaDTO criteria) {
    StringBuilder conditionWhere = new StringBuilder("where 1 = 1 ");
    if (!StringUtils.isEmpty(criteria.getTextSearch())) {
      conditionWhere.append(
          "and match(name, short_desc, full_desc) against(:textSearch in boolean mode) ");
    }
    if (criteria.getAvgRating() != null) {
      conditionWhere.append("and avg_rating >= :avgRating ");
    }
    if (criteria.getStatus() != null) {
      conditionWhere.append("and status = :status ");
    }
    if (criteria.getSupplierId() != null) {
      conditionWhere.append("and supplier_id = :supplierId ");
    }
    if (criteria.getFromUnitPrice() != null) {
      conditionWhere.append("and unit_price >= :fromUnitPrice ");
    }
    if (criteria.getToUnitPrice() != null) {
      conditionWhere.append("and unit_price <= :toUnitPrice ");
    }

    return conditionWhere;
  }

}
