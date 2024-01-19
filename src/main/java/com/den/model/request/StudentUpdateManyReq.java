package com.den.model.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentUpdateManyReq {

    @NotNull(message = "list id student must require")
    private Long[] ids;


    private Integer status;

    private Long clazzId;

    @AssertTrue(message = "Status must null or be between 0 and 4")
    private boolean isStatusValid() {
        if (status == null)
            return false;
        return status >= 0 && status <= 4;
    }

}
