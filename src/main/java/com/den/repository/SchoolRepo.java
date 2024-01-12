package com.den.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.den.entity._school;
import com.den.entity._school_;

@Repository
public class SchoolRepo implements MainRepo<_school, Long> {

  @Autowired
  private Entityql entityql;
  @Autowired
  private NativeSql nativeSql;

  @Override
  public _school insert(_school t) {
    var s = new _school_();
    return entityql.insert(s, t).execute().getEntity();
  }

  @Override
  public boolean update(Long id, _school t) {
    _school_ s = new _school_();
    return nativeSql.update(s)
        .set(v -> {
          v.value(s.adrress, t.getAdrress());
          v.value(s.email, t.getEmail());
          v.value(s.logo, t.getLogo());
          v.value(s.name, t.getName());
        })
        .where(v -> v.eq(s.id, id))
        .execute() == 1;
  }

  @Override
  public boolean delete(Long id) {
    var c = new _school_();
    return nativeSql.delete(c).where(v -> v.eq(c.id, id)).execute() == 1;
  }

  @Override
  public List<_school> getAll() {
    var c = new _school_();
    return entityql.from(c).fetch();
  }

  @Override
  public Optional<_school> findById(Long id) {
    var c = new _school_();
    _school result = entityql.from(c).where(v -> v.eq(c.id, id)).fetchOne();
    return Optional.ofNullable(result);
  }

  @Override
  public Long countAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countAll'");
  }

  public boolean exitsByCode(String name) {
    return exitsByNameNotId(name, null);
  }

  public boolean exitsByNameNotId(String name, Long id) {
    var s = new _school_();
    Optional<_school> optional = nativeSql.from(s)
        .where(v -> {
          v.eq(s.name, name);
          if (id != null)
            v.ne(s.id, id);
        })
        .fetchOptional();
    return optional.isPresent();
  }

}
