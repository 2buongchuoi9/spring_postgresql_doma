package com.den.repository.customPage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Spec {
  private boolean like;
  private String property;
  private String value;
}
