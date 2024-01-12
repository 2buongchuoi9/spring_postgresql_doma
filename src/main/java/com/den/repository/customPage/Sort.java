package com.den.repository.customPage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sort {
  private String property;
  private boolean isAsc = true;

  public static Sort asc(String property) {
    return Sort.by(property);
  }

  public static Sort desc(String property) {
    return new Sort(property, false);
  }

  public static Sort by(String property) {
    return new Sort(property, true);
  }
}
