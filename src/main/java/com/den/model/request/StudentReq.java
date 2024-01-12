package com.den.model.request;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.den.utils._enum.StatusStudentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentReq {
  @NotEmpty(message = "name must not empty")
  private String name;

  @NotNull(message = "clazzId is must require")
  @Min(value = 1, message = "clazzId must be greater than or equal to 1")
  private Long clazzId;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date birthday;

  @NotNull(message = "address is must require")
  private String address;

  private String email;

  private String phone;

  private int status = StatusStudentEnum.ACTIVE.value;

  private String image;

  @AssertTrue(message = "Age must be greater than or equal to 16")
  public boolean isAgeValid() {
    if (birthday == null)
      return true;

    LocalDate birthDate = LocalDate.ofInstant(birthday.toInstant(), java.time.ZoneId.systemDefault());
    LocalDate currentDate = LocalDate.now();

    Period period = Period.between(birthDate, currentDate);
    int age = period.getYears();

    return age >= 16;
  }

  @AssertTrue(message = "email must null or must valid")
  public boolean isEmailValid() {
    if (email == null || email.isEmpty())
      return true;

    return Pattern.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", email);
  }
}
