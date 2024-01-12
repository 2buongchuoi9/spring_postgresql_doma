package com.den.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.den.entity._clazz;
import com.den.entity._clazz_;

@Repository
public class ClazzRepo implements MainRepo<_clazz, Long> {
  @Autowired
  private Entityql entityql;
  @Autowired
  private NativeSql nativeSql;

  @Override
  public _clazz insert(_clazz t) {
    var s = new _clazz_();
    return entityql.insert(s, t).execute().getEntity();
  }

  @Override
  public boolean update(Long id, _clazz t) {
    _clazz_ s = new _clazz_();
    return nativeSql.update(s)
        .set(v -> {
          v.value(s.code, t.getCode());
          v.value(s.schoolId, t.getSchoolId());
          v.value(s.status, t.isStatus());
          v.value(s.dateStart, t.getDateStart());
          v.value(s.dateEnd, t.getDateEnd());
        })
        .where(v -> v.eq(s.id, id))
        .execute() == 1;
  }

  @Override
  public boolean delete(Long id) {
    var c = new _clazz_();
    return nativeSql.delete(c).where(v -> v.eq(c.id, id)).execute() == 1;
  }

  @Override
  public List<_clazz> getAll() {
    var c = new _clazz_();
    return entityql.from(c).fetch();
  }

  @Override
  public Optional<_clazz> findById(Long id) {
    var c = new _clazz_();
    _clazz result = entityql.from(c).where(v -> v.eq(c.id, id)).fetchOne();
    return Optional.ofNullable(result);
  }

  @Override
  public Long countAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countAll'");
  }

  public int countById(Long clazzId) {
    var c = new _clazz_();
    return entityql.from(c).where(v -> v.eq(c.id, clazzId)).execute().size();
  }

  public boolean exitsByCode(String code) {
    return exitsByCodeNotId(code, null);
  }

  public boolean exitsByCodeNotId(String code, Long id) {
    var s = new _clazz_();
    Optional<_clazz> optional = nativeSql.from(s)
        .where(v -> {
          v.eq(s.code, code);
          if (id != null)
            v.ne(s.id, id);
        })
        .fetchOptional();
    return optional.isPresent();
  }

}
