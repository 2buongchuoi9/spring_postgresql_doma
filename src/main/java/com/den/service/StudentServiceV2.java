package com.den.service;

import com.den.dao.ClazzDao;
import com.den.dao.StudentDao;
import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.StudentReq;
import com.den.model.request.StudentUpdateManyReq;
import com.den.model.response.CustomPage;
import com.den.utils._enum;
import org.modelmapper.ModelMapper;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceV2 {
    private final StudentDao studentDao;
    private final ClazzDao clazzDao;
    private final ModelMapper mapper;

    public StudentServiceV2(StudentDao studentDao, ClazzDao clazzDao, ModelMapper mapper) {
        this.studentDao = studentDao;
        this.clazzDao = clazzDao;
        this.mapper = mapper;
    }


    public _student add(StudentReq studentReq) {
//    check email
        if (studentReq.getEmail() != null && !studentReq.getEmail().isEmpty())
            Optional.ofNullable(studentDao.selectByEmail(studentReq.getEmail())).ifPresent(v -> {
                throw new DuplicateRecordError("duplicate email=" + studentReq.getEmail());
            });
//      check clazz
        Optional.ofNullable(clazzDao.selectById(studentReq.getClazzId())).orElseThrow(() -> new BabRequestError("not found classId=" + studentReq.getClazzId()));

//      check member
        int countMember = clazzDao.countById(studentReq.getClazzId());
        if (countMember >= 40)
            throw new BabRequestError("class (" + studentReq.getClazzId() + ") is not more 40 member (current is " + countMember + ")");

        _student student = mapper.map(studentReq, _student.class);
         studentDao.insert(student);
         return studentDao.selectByEmail(studentReq.getEmail());
    }

    public boolean add(_student student) throws BabRequestError {
//    check email
        if (student.getEmail() != null && !student.getEmail().isEmpty())
            Optional.ofNullable(studentDao.selectByEmail(student.getEmail())).ifPresent(v -> {
                throw new BabRequestError("duplicate email=" + student.getEmail());
            });
//      check clazz
        Optional.ofNullable(clazzDao.selectById(student.getClazzId())).orElseThrow(() -> new BabRequestError("not found classId=" + student.getClazzId()));

//      check member
        int countMember = clazzDao.countById(student.getClazzId());
        if (countMember >= 40)
            throw new BabRequestError("class (" + student.getClazzId() + ") is not more 40 member (current is " + countMember + ")");


        return studentDao.insert(student) == 1;
    }


    public _student findById(Long id) {
        return Optional.ofNullable(studentDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found school id=" + id));
    }

    public Optional<_student> findByIdOptional(Long id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(studentDao.selectById(id));
    }

    public List<_student> findAll() {
        return studentDao.selectAll();
    }

    public PageImpl<_student> findAll(Pageable pageable) {
        return findAll(pageable, null);
    }

    public PageImpl<_student> findAll(Pageable pageable, Integer status) {

        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber()) * limit;


        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_student> list;
        int total;

        if (status != null && status < 5 && status > -1) {
            list = studentDao.selectByStatus(options, status);
            total = studentDao.countByStatus(status);
        } else {
            list = studentDao.selectAll(options);
            total = studentDao.countAll();
        }

        return new PageImpl<>(list, pageable, total);
    }

    public PageImpl<_student> search(Pageable pageable, String keySearch) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber()) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_student> list = studentDao.selectByKeySearch(keySearch, options);
        int total = studentDao.countByKeySearch(keySearch);

        return new PageImpl<>(list, pageable, total);
    }


    public PageImpl<_student> findByClazzId(Pageable pageable, Long clazzId, Integer status) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber()) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        if ((status != null && status > 4) || (status != null && status < 0))
            throw new BabRequestError("status must from 0 to 4");

        List<_student> list = studentDao.selectByClazzId(options, clazzId, status);
        int total = studentDao.countByClazzId(clazzId, status);

        return new PageImpl<>(list, pageable, total);
    }


    public PageImpl<_student> findBySchoolId(Pageable pageable, Long schoolId, Integer status) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber()) * limit;

        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        if ((status != null && status > 4) || (status != null && status < 0))
            throw new BabRequestError("status must from 0 to 4");

        List<_student> list = studentDao.selectBySchoolId(options, schoolId, status);
        int total = studentDao.countBySchoolId(schoolId, status);

        return new PageImpl<>(list, pageable, total);
    }


    public boolean delete(Long id) {
        _student student = Optional.ofNullable(studentDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found studentId+" + id));
        student.setStatus(_enum.StatusStudentEnum.OFF.value);
        return studentDao.update(student) == 1;
    }


    public _student update(Long id, StudentReq studentReq) {
        Optional.ofNullable(studentDao.selectByEmailNotId(studentReq.getEmail(), id)).ifPresent(v -> {
            throw new DuplicateRecordError("duplicate email=" + studentReq.getEmail());
        });

        Optional.ofNullable(clazzDao.selectById(studentReq.getClazzId())).orElseThrow(() ->
                new NotFoundError("not found clazzId=" + studentReq.getClazzId()));

        int countMember = clazzDao.countById(studentReq.getClazzId());
        if (countMember >= 40)
            throw new BabRequestError("class (" + studentReq.getClazzId() + ") is not more 40 member (current is " + countMember + ")");

        _student clazz = mapper.map(studentReq, _student.class);
        clazz.setId(id);
         studentDao.update(clazz);
         return studentDao.selectById(id);
    }

    public boolean update(_student student) throws BabRequestError {
        Optional.ofNullable(studentDao.selectByEmailNotId(student.getEmail(), student.getId())).ifPresent(v -> {
            throw new BabRequestError("duplicate email=" + student.getEmail());
        });

        Optional.ofNullable(clazzDao.selectById(student.getClazzId())).orElseThrow(() ->
                new BabRequestError("not found clazzId=" + student.getClazzId()));

        int countMember = clazzDao.countById(student.getClazzId());
        if (countMember >= 40)
            throw new BabRequestError("class (" + student.getClazzId() + ") is not more 40 member (current is " + countMember + ")");

        return studentDao.update(student) == 1;
    }

    public boolean updateMany(StudentUpdateManyReq studentUpdateManyReq) {
        boolean isStatus = studentUpdateManyReq.getStatus() != null;
        boolean isClazzId = studentUpdateManyReq.getClazzId() != null;
        List<_student> list = Arrays.stream(studentUpdateManyReq.getIds())
                .map(v -> {
                    _student std = new _student();
                    std.setId(v);
                    std.setClazzId(studentUpdateManyReq.getClazzId());
                    std.setStatus(studentUpdateManyReq.getStatus());
                    return std;
                }).toList();


        if(isStatus) studentDao.updateManySetStatus(list);
        if(isClazzId) studentDao.updateManySetClazzId(list);

        return true;
    }
}
