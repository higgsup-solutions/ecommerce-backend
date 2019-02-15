package com.higgsup.base.controller;

import com.higgsup.base.dto.CopyDemo2DTO;
import com.higgsup.base.dto.CopyDemoDTO;
import com.higgsup.base.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/public")
@Validated
public class TestController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/test")
  public ResponseEntity<String> ping() {
    logger.debug("Debugging log");
    logger.info("Info log");
    logger.warn("Hey, This is a warning!");
    logger.error("Oops! We have an Error. OK");
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/testBeanCopy")
  public ResponseEntity<CopyDemo2DTO> testBeanCopy() {
    CopyDemoDTO copyDemoDTO = new CopyDemoDTO();
    copyDemoDTO.setName("Test");
    UserDTO userDTO = new UserDTO();
    userDTO.setId(1L);
    userDTO.setUserName("anhvuong@gmail.com");
    copyDemoDTO.setUserDTO(userDTO);
    CopyDemo2DTO copyDemo2DTO = new CopyDemo2DTO();
    BeanUtils.copyProperties(copyDemoDTO, copyDemo2DTO);
    return ResponseEntity.ok(copyDemo2DTO);
  }

  @GetMapping("/validatePathVariable/{id}")
  ResponseEntity<String> validatePathVariable(
      @PathVariable("id") @Min(5) int id) {
    return ResponseEntity.ok("valid");
  }
}
