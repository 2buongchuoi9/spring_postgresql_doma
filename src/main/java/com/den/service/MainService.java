package com.den.service;

import java.util.List;

public interface MainService<T, T_, ID> {

  public T_ add(T t);

  public T_ findById(ID id);

  public T_ update(ID id, T t);

  public List<T_> findAll();

  public boolean delete(ID id);

}
