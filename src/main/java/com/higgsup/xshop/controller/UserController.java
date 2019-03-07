package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.UserDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@Api(value = "UserController", description = "Set of methods user")
public class UserController {

  private final IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  @ApiOperation(value = "API get current user info", response = IPagedResponse.class)
  public IPagedResponse<UserDTO> getCurrentUserInfo() {
    ResponseMessage<UserDTO> responseMessage = new ResponseMessage<>();
    responseMessage.setData(userService.getCurrentUserInfo());
    return new IPagedResponse<>(responseMessage);
  }


}
