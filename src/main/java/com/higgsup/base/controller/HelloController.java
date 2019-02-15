package com.higgsup.base.controller;

import com.higgsup.base.common.ErrorCode;
import com.higgsup.base.dto.DemoDTO;
import com.higgsup.base.dto.UserDTO;
import com.higgsup.base.dto.base.ResponseMessage;
import com.higgsup.base.entity.User;
import com.higgsup.base.exception.BusinessException;
import com.higgsup.base.locale.Translator;
import com.higgsup.base.log.RequestLogger;
import com.higgsup.base.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value="helloController", description="Hello controller description")
public class HelloController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final IUserService userService;

  public HelloController(IUserService userService) {
    this.userService = userService;
  }

  @GetMapping("/ping")
  @ApiOperation(value = "Description method", response = ResponseEntity.class)
  public ResponseEntity ping() {
    return ResponseEntity.ok(Translator.toLocale("hello"));
  }

  @PostMapping("/create")
  public ResponseEntity<UserDTO> create(
      @RequestBody UserDTO userDTO) {
    userService.createUser(userDTO);
    return ResponseEntity.ok(userDTO);
  }

  @GetMapping("/user")
  @RequestLogger
  public DemoDTO getUser(HttpServletRequest request) {
    DemoDTO demoDTO = new DemoDTO();
    ResponseMessage<List<User>> responseMessage = new ResponseMessage<>();
    responseMessage.setData(userService.getUser());
    responseMessage.setStatus(HttpStatus.OK.getReasonPhrase());
    demoDTO.setResponseMessage(responseMessage);
    return demoDTO;
  }

  @GetMapping("/exception")
  @RequestLogger
  public ResponseEntity getException(HttpServletRequest request) {
    throw new BusinessException(ErrorCode.GLOBAL, "RuntimeException...");
  }

  @GetMapping("/exception-null")
  @RequestLogger
  public ResponseEntity getExceptionNull(HttpServletRequest request) {
   DemoDTO demo = null;
   demo.getResponseMessage();
   return ResponseEntity.ok(demo);
  }


}
