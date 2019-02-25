package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.RatingDTO;
import com.higgsup.xshop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {

  Page<Review> findAllByProduct_Id (Pageable pageRequest, Integer id);

}
