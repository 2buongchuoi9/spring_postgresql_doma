package com.den.model.request;

import java.util.Date;

import org.seasar.doma.Column;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClazzReq {

  @NotEmpty(message = "code is must require")
  private String code;

  @NotNull(message = "schoolId is must require")
  @Min(value = 1, message = "schoolId must be greater than or equal to 1")
  private Long schoolId;

  private boolean status = true;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date dateStart;

  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date dateEnd;

  @AssertTrue(message = "End date must be at least 30 days after start date")
  public boolean isDateValid() {
    if (dateStart == null || dateEnd == null) {
      return true;
    }
    long diff = dateEnd.getTime() - dateStart.getTime();
    long diffDays = diff / (24 * 60 * 60 * 1000);
    return diffDays >= 30;
  }
}
