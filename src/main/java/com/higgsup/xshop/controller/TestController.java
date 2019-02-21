package com.higgsup.xshop.controller;

import com.higgsup.xshop.dto.CopyDemo2DTO;
import com.higgsup.xshop.dto.CopyDemoDTO;
import com.higgsup.xshop.dto.UserDTO;
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

  @GetMapping("/validatePathVariable/{id}")
  ResponseEntity<String> validatePathVariable(
      @PathVariable("id") @Min(5) int id) {
    return ResponseEntity.ok("valid");
  }
}
