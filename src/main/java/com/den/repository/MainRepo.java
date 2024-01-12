package com.den.repository;

import java.util.List;
import java.util.Optional;

public interface MainRepo<T, ID> {

  T insert(T t);

  boolean update(ID id, T t);

  boolean delete(ID id);

  List<T> getAll();

  Long countAll();

  Optional<T> findById(ID id);

}