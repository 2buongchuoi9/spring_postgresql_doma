package com.den.repository.customPage;

import java.util.List;

public class PageImpl<T> implements Page<T> {

  private List<T> content;
  private Pageble pageble;
  private int total;

  public PageImpl(List<T> content, Pageble pageble, int total) {
    this.content = content;
    this.pageble = pageble;
    this.total = total;
  }

  @Override
  public List<T> getContent() {
    return this.content;
  }

  @Override
  public int getPageSize() {
    return this.pageble.getPageSize();
  }

  @Override
  public int getCurrentPage() {
    return this.pageble.getPageNumber();
  }

  @Override
  public int getTotalPage() {
    return (int) Math.ceil(this.total / this.pageble.getPageSize() + 1);
  }

  @Override
  public int getTotalElement() {
    return this.total;
  }

}
