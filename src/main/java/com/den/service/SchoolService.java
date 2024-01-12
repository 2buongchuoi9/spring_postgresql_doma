package com.den.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._clazz;
import com.den.entity._school;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.SchoolReq;
import com.den.repository.SchoolRepo;

@Service
public class SchoolService implements MainService<SchoolReq, _school, Long> {

  @Autowired
  private SchoolRepo schoolRepo;
  @Autowired
  private ModelMapper mapper;

  @Override
  public _school add(SchoolReq t) {
    _school school = mapper.map(t, _school.class);
    return schoolRepo.insert(school);
  }

  @Override
  public _school findById(Long id) {
    return schoolRepo.findById(id).orElseThrow(() -> new NotFoundError("not found school id=" + id));
  }

  @Override
  public List<_school> findAll() {
    return schoolRepo.getAll();
  }

  @Override
  public boolean delete(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public _school update(Long id, SchoolReq schoolReq) {
    if (schoolRepo.exitsByNameNotId(schoolReq.getName(), id))
      throw new DuplicateRecordError("name is duplicate");

    _school school = mapper.map(schoolReq, _school.class);
    if (!schoolRepo.update(id, school))
      throw new UnKnowError("update fail");
    return findById(id);
  }

}
