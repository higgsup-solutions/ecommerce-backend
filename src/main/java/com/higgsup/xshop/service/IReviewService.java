package com.higgsup.xshop.service;

import com.higgsup.xshop.dto.ReviewDTO;

import java.util.List;

public interface IReviewService {
  List<ReviewDTO> getFeedBackByProductId(Integer id);
}