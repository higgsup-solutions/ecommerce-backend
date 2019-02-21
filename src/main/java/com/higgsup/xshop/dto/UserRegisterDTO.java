
package com.higgsup.xshop.dto;

import com.higgsup.xshop.common.ApplicationConstant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterDTO {

  private String emailRegex;

  @NotNull(message = "First name is a required field")
  private String firstName;

  @NotNull(message = "Last name is a required field")
  private String lastName;

  @Pattern(regexp = ApplicationConstant.EMAIL_REGEX , message = "Email is not valid")
  private String email;

  @NotNull(message = "Last name is a required field")
  private String password;



}
