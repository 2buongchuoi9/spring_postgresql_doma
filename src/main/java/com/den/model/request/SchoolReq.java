package com.den.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SchoolReq {
  @NotEmpty(message = "name must not empty")
  private String name;

  @NotEmpty(message = "address must not empty")
  private String address;
  @NotEmpty(message = "email must not empty")
  @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;

  private String logo;
}
