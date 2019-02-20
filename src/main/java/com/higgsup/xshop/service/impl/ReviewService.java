package com.higgsup.xshop.service.impl;

import com.higgsup.xshop.dto.ReviewDTO;
import com.higgsup.xshop.entity.Review;
import com.higgsup.xshop.repository.ReviewRepository;
import com.higgsup.xshop.service.IReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReviewService implements IReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewService(
      ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  @Override
  public List<ReviewDTO> getFeedBackByProductId(Integer id) {
    List<ReviewDTO> reviewDTOS = new ArrayList<>();
    Pageable pageRequest = PageRequest
        .of(0, 5, Sort.Direction.DESC, "createdDate");
    Page<Review> reviews = reviewRepository.findAllByProduct_Id(pageRequest, id);
    reviews.getContent().forEach(review -> {
      ReviewDTO reviewDTO = new ReviewDTO();
      BeanUtils.copyProperties(review, reviewDTO);
      reviewDTOS.add(reviewDTO);
        });
    return reviewDTOS;
  }
}
