package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Order;
import org.springframework.data.domain.Page;

public interface OrderRespositoryCustom  {

    Page<Order> findListOrderLast6Months(Integer userId, int pageIndex, int pageSize);
}
