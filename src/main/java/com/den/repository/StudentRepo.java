package com.den.repository;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.den.entity._school_;
import com.den.entity._student;
import com.den.entity._student_;

@Repository
public class StudentRepo implements MainRepo<_student, Long> {
  @Autowired
  private Entityql entityql;
  @Autowired
  private NativeSql nativeSql;

  @Override
  public _student insert(_student t) {
    var c = new _student_();
    return entityql.insert(c, t).execute().getEntity();
  }

  @Override
  public boolean update(Long id, _student t) {
    _student_ s = new _student_();
    return nativeSql.update(s)
        .set(v -> {
          v.value(s.name, t.getName());
          v.value(s.clazzId, t.getClazzId());
          v.value(s.birthday, t.getBirthday());
          v.value(s.adrress, t.getAdrress());
          v.value(s.email, t.getEmail());
          v.value(s.phone, t.getPhone());
          v.value(s.status, t.getStatus());
          v.value(s.image, t.getImage());
        })
        .where(v -> v.eq(s.id, id))
        .execute() == 1;
  }

  @Override
  public boolean delete(Long id) {
    var c = new _student_();
    return nativeSql.delete(c).where(v -> v.eq(c.id, id)).execute() == 1;
  }

  @Override
  public List<_student> getAll() {
    var c = new _student_();
    return entityql.from(c).fetch();
  }

  @Override
  public Optional<_student> findById(Long id) {
    var c = new _student_();
    _student result = entityql.from(c).where(v -> v.eq(c.id, id)).fetchOne();
    return Optional.ofNullable(result);
  }

  @Override
  public Long countAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'countAll'");
  }

  public boolean exitsByEmail(String email) {
    return exitsByEmailNotId(email, null);
  }

  public boolean exitsByEmailNotId(String email, Long id) {
    var s = new _student_();
    Optional<_student> optional = nativeSql.from(s)
        .where(v -> {
          v.eq(s.email, email);
          v.isNotNull(s.email);
          if (id != null)
            v.ne(s.id, id);
        })
        .fetchOptional();
    return optional.isPresent();
  }

}
