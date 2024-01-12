package com.den.repository.customPage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Spec {
  private Boolean like = null;
  private Boolean equals;
  private String property;
  private String value;

}
