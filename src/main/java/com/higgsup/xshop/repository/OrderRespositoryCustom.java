package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRespositoryCustom  {

    Page<Order> findListOrderLast6Months(Integer userId, Pageable pageable);
}
