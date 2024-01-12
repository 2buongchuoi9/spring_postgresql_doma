package com.den.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._clazz;
import com.den.entity._clazz;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.ClazzReq;
import com.den.repository.ClazzRepo;
import com.den.repository.SchoolRepo;

@Service
public class ClazzService implements MainService<ClazzReq, _clazz, Long> {
  @Autowired
  private ClazzRepo clazzRepo;
  @Autowired
  private SchoolRepo schoolRepo;

  @Autowired
  private ModelMapper mapper;

  @Override
  public _clazz add(ClazzReq t) {
    if (clazzRepo.exitsByCode(t.getCode()))
      throw new DuplicateRecordError("duplicate code=" + t.getCode());

    if (!schoolRepo.findById(t.getSchoolId()).isPresent())
      throw new BabRequestError("not found schoolId=" + t.getSchoolId());
    _clazz clazz = mapper.map(t, _clazz.class);
    return clazzRepo.insert(clazz);
  }

  @Override
  public _clazz findById(Long id) {
    return clazzRepo.findById(id).orElseThrow(() -> new NotFoundError("not found school id=" + id));
  }

  @Override
  public List<_clazz> findAll() {
    return clazzRepo.getAll();
  }

  @Override
  public boolean delete(Long id) {
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  @Override
  public _clazz update(Long id, ClazzReq clazzReq) {
    if (clazzRepo.exitsByCodeNotId(clazzReq.getCode(), id))
      throw new DuplicateRecordError("name is duplicate");

    if (!schoolRepo.findById(clazzReq.getSchoolId()).isPresent())
      throw new BabRequestError("Not found school id=" + clazzReq.getSchoolId());

    _clazz clazz = mapper.map(clazzReq, _clazz.class);
    if (!clazzRepo.update(id, clazz))
      throw new UnKnowError("update fail");
    return findById(id);
  }

}
