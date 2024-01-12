package com.den.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._student;
import com.den.entity._student_;
import com.den.entity._student;
import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.StudentReq;
import com.den.model.request.StudentReq;
import com.den.repository.StudentRepo;
import com.den.repository.customPage.Page;
import com.den.repository.customPage.Pageble;
import com.den.repository.ClazzRepo;
import com.den.repository.SchoolRepo;
import com.den.repository.StudentRepo;

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

    int countMember = clazzRepo.countById(t.getClazzId());
    if (countMember >= 40)
      throw new BabRequestError(
          "class (" + t.getClazzId() + ") is not more 40 member (current is " + countMember + ")");

    _student student = mapper.map(t, _student.class);
    return studentRepo.insert(student);
  }

  @Override
  public _student findById(Long id) {
    return studentRepo.findById(id).orElseThrow(() -> new NotFoundError("not found school id=" + id));
  }

  @Override
  public List<_student> findAll() {
    return studentRepo.getAll();
  }

  public Page<_student> findAll(Pageble pageble) {
    return studentRepo.findAll(pageble);
  }

  public Page<_student> search(Pageble pageble, String keySearch) {
    return studentRepo.search(pageble, keySearch);
  }

  public Page<_student> findByClazzId(Pageble pageble, Long clazzId) {
    return studentRepo.findByClazzId(pageble, clazzId);
  }

  public Page<_student> findBySchoolId(Pageble pageble, Long schoolId) {
    return studentRepo.findBySchoolId(pageble, schoolId);
  }

  @Override
  public boolean delete(Long id) {
    if (!studentRepo.findById(id).isPresent())
      throw new NotFoundError("not found studentId=" + id);

    return studentRepo.delete(id);

  }

  @Override
  public _student update(Long id, StudentReq studentReq) {
    if (studentRepo.exitsByEmailNotId(studentReq.getEmail(), id))
      throw new DuplicateRecordError("name is duplicate");

    if (!clazzRepo.findById(studentReq.getClazzId()).isPresent())
      throw new BabRequestError("Not found classId=" + studentReq.getClazzId());

    int countMember = clazzRepo.countById(studentReq.getClazzId());
    if (countMember >= 40)
      throw new BabRequestError(
          "class (" + studentReq.getClazzId() + ") is not more 40 member (current is " + countMember + ")");

    _student clazz = mapper.map(studentReq, _student.class);
    if (!studentRepo.update(id, clazz))
      throw new UnKnowError("update fail");
    return findById(id);
  }

}
