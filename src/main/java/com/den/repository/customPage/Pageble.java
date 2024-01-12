package com.den.repository.customPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageble {
  private int pageNumber;
  private int pageSize;
  private Sort sort;

  public Pageble(int pageNumber, int pageSize) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
  }

  public static Pageble of(int pageNumber, int pageSize, Sort sort) {
    return new Pageble(pageNumber, pageSize, sort);
  }

  public static Pageble of(int pageNumber, int pageSize) {
    return new Pageble(pageNumber, pageSize);
  }
}
