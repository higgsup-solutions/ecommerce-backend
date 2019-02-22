package com.higgsup.xshop.controller;

import com.higgsup.xshop.common.ErrorCode;
import com.higgsup.xshop.dto.UserRegisterDTO;
import com.higgsup.xshop.dto.base.IPagedResponse;
import com.higgsup.xshop.dto.base.ResponseMessage;
import com.higgsup.xshop.entity.User;
import com.higgsup.xshop.exception.BusinessException;
import com.higgsup.xshop.service.IUserService;
import com.higgsup.xshop.service.impl.ValidationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

  private IUserService userService;

  private ValidationService validationService;

  public RegisterController(IUserService userService, ValidationService validationService) {
    this.userService = userService;
    this.validationService = validationService;
  }

  @PostMapping()
  @ApiOperation(value = "API register", response = IPagedResponse.class)
  public ResponseEntity<IPagedResponse<User>> doRegister(
      @RequestBody UserRegisterDTO userRegisterDTO) {
    if (!this.validationService.validate(userRegisterDTO)) {
      throw new BusinessException(ErrorCode.VALIDATION,
          ErrorCode.VALIDATION.getErrorMessage());
    }
    User newUser = new User();
    BeanUtils.copyProperties(userRegisterDTO, newUser);
    ResponseMessage<User> responseMessage = new ResponseMessage<>();
    responseMessage.setData(this.userService.register(newUser));
    return ResponseEntity.ok(new IPagedResponse<>(responseMessage));
  }
}
