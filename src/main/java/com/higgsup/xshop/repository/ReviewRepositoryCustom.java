package com.higgsup.xshop.repository;

import com.higgsup.xshop.dto.RatingDTO;

import java.util.List;

public interface ReviewRepositoryCustom {

  List<RatingDTO> countRatingByProductId(Integer productId);
}
