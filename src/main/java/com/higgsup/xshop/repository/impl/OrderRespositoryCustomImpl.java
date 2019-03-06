package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.entity.Order;
import com.higgsup.xshop.repository.OrderRespositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
public class OrderRespositoryCustomImpl implements OrderRespositoryCustom {

    private final EntityManager entityManager;

    public OrderRespositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Page<Order> findListOrderLast6Months(Integer userId, int pageIndex, int pageSize) {
        String sql = "select * from `order` where user_id = :userId (created_date between (NOW() - INTERVAL 1 MONTH) AND NOW())";
        Query query = entityManager.createNativeQuery(sql)
                .setParameter("userId", userId);
        return new JpaResultConverter().list(query, BreadcrumbDTO.class);
    }
}
