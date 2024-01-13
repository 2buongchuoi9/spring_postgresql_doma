package com.den.model;

import com.den.entity._student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectResultExcel {

    public static  final String FAIL="fail",UPDATED="updated",INSERTED="inserted";
    private int stt;
    private String status;
    private String error;



}
