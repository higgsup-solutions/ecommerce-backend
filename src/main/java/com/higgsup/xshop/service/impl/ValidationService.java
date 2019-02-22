package com.higgsup.xshop.service.impl;

import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationService {

  private final Validator validator;

  public ValidationService(Validator validator) {
    this.validator = validator;
  }

  public boolean validate(Object obj) {
    Set<ConstraintViolation<Object>> validations = this.validator.validate(obj);
    return validations.size() > 0;
  }

}
