package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.ReviewDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.IReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@Api(value = "ReviewController", description = "Set of methods related to reviews")
public class ReviewController {

  private final IReviewService reviewService;

  public ReviewController(
      IReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/feedback/{id}")
  @ApiOperation(value = "API get feedback review by product id", response = IPagedResponse.class)
  public IPagedResponse<List<ReviewDTO>> getFeedback(@PathVariable("id") Integer id){
    IPagedResponse<List<ReviewDTO>> iPagedResponse = new IPagedResponse<>();
    ResponseMessage<List<ReviewDTO>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(reviewService.getFeedBackByProductId(id));
    iPagedResponse.setResponseMessage(responseMessage);
    return iPagedResponse;
  }
}
