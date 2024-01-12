package com.den.repository.customPage;

import java.util.List;

public interface Page<T> {
  List<T> getContent();

  int getPageSize(); // số lượng phần tử trên 1 trang

  int getCurrentPage(); // trnag hiện tại

  int getTotalPage(); // tổng số lượng trang

  int getTotalElement(); // tổng số lượn phần tử

}
