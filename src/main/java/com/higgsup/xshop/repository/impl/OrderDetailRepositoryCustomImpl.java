package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.dto.OrderProductDTO;
import com.higgsup.xshop.repository.OrderDetailRepositoryCustom;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class OrderDetailRepositoryCustomImpl implements
    OrderDetailRepositoryCustom {

  private final EntityManager entityManager;

  public OrderDetailRepositoryCustomImpl(
      EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<OrderProductDTO> findProductOrderByOrderId(Integer orderId) {
    String sql = "select "
        + "product.name as name,"
        + "order_detail.quantity as quantity,"
        + "order_detail.status as status,"
        + "product.img_url as imgUrl"
        + " from "
        + "user_order,"
        + "order_detail,"
        + "product"
        + " where "
        + "user_order.id = order_detail.order_id"
        + " and order_detail.product_id = product.id"
        + " and user_order.id = :orderId ;";

    List<OrderProductDTO> listResult = entityManager.createNativeQuery(sql)
        .setParameter("orderId", orderId).unwrap( org.hibernate.query.NativeQuery.class )
        .setResultTransformer( Transformers.aliasToBean( OrderProductDTO.class ) )
        .getResultList();
    return listResult;
  }
}
