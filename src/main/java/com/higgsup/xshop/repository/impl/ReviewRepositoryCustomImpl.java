package com.higgsup.xshop.repository.impl;

import com.higgsup.xshop.common.JpaResultConverter;
import com.higgsup.xshop.dto.RatingDTO;
import com.higgsup.xshop.repository.ReviewRepositoryCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ReviewRepositoryCustomImpl implements
    ReviewRepositoryCustom {

  private final EntityManager entityManager;

  public ReviewRepositoryCustomImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<RatingDTO> countRatingByProductId(Integer productId) {
    String sql = "select rating, count(id) as counting from review where product_id = :productId group by rating order by rating desc";
    Query query = entityManager.createNativeQuery(sql)
        .setParameter("productId", productId);
    return new JpaResultConverter().list(query, RatingDTO.class);
  }
}
