package com.den.service;

import com.den.dao.SchoolDao;
import com.den.entity._school;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.model.request.SchoolReq;
import org.modelmapper.ModelMapper;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceV2 {

  private final SchoolDao schoolDao;
  private final ModelMapper mapper;

  public SchoolServiceV2(SchoolDao schoolDao, ModelMapper mapper) {
    this.schoolDao = schoolDao;
    this.mapper = mapper;
  }

  public boolean add(SchoolReq t) {

    if (Optional.ofNullable(schoolDao.selectByName(t.getName())).isPresent())
      throw new DuplicateRecordError("duplicate name: " + t.getName());

    _school school = mapper.map(t, _school.class);
    return schoolDao.insert(school) == 1;
  }

  public Page<_school> findAll(Pageable pageable) {
    int limit = pageable.getPageSize();
    int offset = (pageable.getPageNumber()) * limit;
    SelectOptions options = SelectOptions.get().limit(limit).offset(offset);
    List<_school> list = schoolDao.selectAll(options);
    int total = schoolDao.countAll(options);
    return new PageImpl<>(list, pageable, total);
  }

  public _school findById(Long id) {
    return Optional.ofNullable(schoolDao.findById(id))
        .orElseThrow(() -> new NotFoundError("not found school id=" + id));
  }

  public boolean update(Long id, SchoolReq schoolReq) {
    Optional.ofNullable(schoolDao.findById(id)).orElseThrow(() -> new NotFoundError("not found school id=" + id));

    _school school = mapper.map(schoolReq, _school.class);
    school.setId(id);
    return schoolDao.update(school) == 1;
  }
}
