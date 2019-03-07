package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.repository.OrderRespositoryCustom;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class OrderRespositoryCustomImpl implements OrderRespositoryCustom {

  private final EntityManager entityManager;

  public OrderRespositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Page<Order> findListOrderLast6Months(Integer userId, Pageable pageable) {
    String sql = "select id as id, total_amount as totalAmount, discount_amount as discountAmount, created_date as createdDate from user_order where user_id = :userId and now() >= now()-interval 6 month";

    String sqlCount = "select count(id) from `user_order` where user_id = :userId";

    Query query = entityManager.createNativeQuery(sql)
        .setParameter("userId", userId);

    query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
    query.setMaxResults(pageable.getPageSize());

    List<Order> dataList = query.unwrap( org.hibernate.query.NativeQuery.class )
        .setResultTransformer( Transformers.aliasToBean( Order.class ) )
        .getResultList();

    Query queryCount = entityManager.createNativeQuery(sqlCount)
        .setParameter("userId", userId);

    BigInteger totalSize = (BigInteger) queryCount.getSingleResult();

    return new PageImpl<>(dataList, pageable, totalSize.longValue());
  }
}
