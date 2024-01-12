package com.den.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MainRes {
  private int code = 200;
  private String status = "ok";
  private Object metadata;

  public MainRes(Object metadata) {
    this.metadata = metadata;
  }

}
