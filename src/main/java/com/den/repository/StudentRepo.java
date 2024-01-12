package com.den.repository;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.poi.ss.formula.functions.T;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.jdbc.criteria.Entityql;
import org.seasar.doma.jdbc.criteria.NativeSql;
import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.den.entity._school_;
import com.den.entity._student;
import com.den.entity._student_;
import com.den.exceptions.UnKnowError;

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
                    v.value(s.address, t.getAddress());
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

    public Page<_student> findAll(Pageable pageable) {
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = pageable.getPageSize();

        Sort sort = pageable.getSort();

        _student_ c = new _student_();

        List<_student> list = nativeSql.from(c)
                .limit(limit)
                .offset(offset)
                .execute();

        int total = nativeSql.from(c).execute().size();

        Page<_student> page = new PageImpl<>(list, pageable, total);
        return page;
    }

    public Page<_student> findByClazzId(Pageable pageable, Long id) {
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = pageable.getPageSize();

        Sort sort = pageable.getSort();

        var c = new _student_();

        List<_student> list = nativeSql.from(c)
                .where(v -> {
                    v.eq(c.clazzId, id);
                })
                .limit(limit)
                .offset(offset)
                .execute();

        int total = nativeSql.from(c)
                .where(v -> {
                    v.eq(c.clazzId, id);
                })
                .execute()
                .size();

        Page<_student> page = new PageImpl<>(list, pageable, total);
        return page;
    }

    public Page<_student> findBySchoolId(Pageable pageable, Long id) {
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = pageable.getPageSize();

        Sort sort = pageable.getSort();

        var c = new _student_();
        var s = new _school_();

        List<_student> list = nativeSql.from(c)
                .innerJoin(s, on -> on.eq(c.clazzId, s.id))
                .where(v -> v.eq(s.id, id))
                .limit(limit)
                .offset(offset)
                .execute();

        int total = nativeSql.from(c)
                .where(v -> v.eq(s.id, id))
                .execute()
                .size();

        Page<_student> page = new PageImpl<>(list, pageable, total);
        return page;
    }

    public Page<_student> search(Pageable pageable, String keySearch) {
        int offset = (pageable.getPageNumber() - 1) * pageable.getPageSize();
        int limit = pageable.getPageSize();

        Sort sort = pageable.getSort();

        var c = new _student_();

        List<_student> list = nativeSql.from(c)
                .where(v -> {
                    v.or(() -> v.like(c.name, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.code, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.address, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.email, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.phone, "%" + keySearch + "%"));
                })
                .limit(limit)
                .offset(offset)
                .execute();

        int total = nativeSql.from(c)
                .where(v -> {
                    v.or(() -> v.like(c.name, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.code, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.address, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.email, "%" + keySearch + "%"));
                    v.or(() -> v.like(c.phone, "%" + keySearch + "%"));
                })
                .execute()
                .size();

        Page<_student> page = new PageImpl<>(list, pageable, total);
        return page;
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

    private PropertyMetamodel<?> getProperty(String name) {
        _student_ c = new _student_();
        List<PropertyMetamodel<?>> list = c.allPropertyMetamodels();
        return list.stream().filter(v -> v.getName().equals(name)).findFirst()
                .orElseThrow(() -> new UnKnowError("error dev in property name"));
    }

}
