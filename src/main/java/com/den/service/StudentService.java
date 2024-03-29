package com.den.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.StudentReq;
import com.den.repository.StudentRepo;
import com.den.repository.ClazzRepo;

@Service
public class StudentService implements MainService<StudentReq, _student, Long> {
  @Autowired
  private StudentRepo studentRepo;
  @Autowired
  private ClazzRepo clazzRepo;

  @Autowired
  private ModelMapper mapper;

  @Override
  public _student add(StudentReq t) {
    if (studentRepo.exitsByEmail(t.getEmail()))
      throw new DuplicateRecordError("duplicate code=" + t.getEmail());

    if (!clazzRepo.findById(t.getClazzId()).isPresent())
      throw new BabRequestError("not found classId=" + t.getClazzId());
    _student student = mapper.map(t, _student.class);
    return studentRepo.insert(student);
  }

  @Override
  public _student findById(Long id) {
    return studentRepo.findById(id).orElseThrow(() -> new NotFoundError("not found school id=" + id));
  }

  public List<_student> findAll() {
    return studentRepo.getAll();
  }

  @Override
  public boolean delete(Long id) {
    if (studentRepo.findById(id).isEmpty())
      throw new NotFoundError("not found studentId=" + id);

    return studentRepo.delete(id);

  }

  @Override
  public _student update(Long id, StudentReq studentReq) {
    if (studentRepo.exitsByEmailNotId(studentReq.getEmail(), id))
      throw new DuplicateRecordError("name is duplicate");

    if (!clazzRepo.findById(studentReq.getClazzId()).isPresent())
      throw new BabRequestError("Not found classId=" + studentReq.getClazzId());

    _student clazz = mapper.map(studentReq, _student.class);
    if (!studentRepo.update(id, clazz))
      throw new UnKnowError("update fail");
    return findById(id);
  }
  public Page<_student> findAll(Pageable pageable) {
    return studentRepo.findAll(pageable);
  }

  public Page<_student> search(Pageable pageable, String keySearch) {
    return studentRepo.search(pageable, keySearch);
  }

  public Page<_student> findByClazzId(Pageable pageable, Long clazzId) {
    return studentRepo.findByClazzId(pageable, clazzId);
  }

  public Page<_student> findBySchoolId(Pageable pageable, Long schoolId) {
    return studentRepo.findBySchoolId(pageable, schoolId);
  }

}
