package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRespositoryCustom {

    List<Order> findFirst5ByUserId(Integer userId);

    Page<Order> findDistinctByUserId(Integer userId, Pageable pageable);


}
