package com.higgsup.xshop.repository;

import com.higgsup.xshop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

  Page<Review> findAllByProduct_Id (Pageable pageRequest, Integer id);
}
