package com.den.service;

import com.den.dao.ClazzDao;
import com.den.dao.StudentDao;
import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.exceptions.UnKnowError;
import com.den.model.request.StudentReq;
import com.den.utils._enum;
import org.modelmapper.ModelMapper;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public boolean add(StudentReq studentReq) {
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
        return studentDao.insert(student) == 1;
    }


    public _student findById(Long id) {
        return Optional.ofNullable(studentDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found school id=" + id));
    }


    public Page<_student> findAll(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber() - 1) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_student> list = studentDao.selectAll(options);
        int total = studentDao.countAll();
        return new PageImpl<>(list, pageable, total);
    }

    public Page<_student> search(Pageable pageable, String keySearch) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber() - 1) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_student> list = studentDao.selectByKeySearch(keySearch, options);
        int total = studentDao.countByKeySearch(keySearch);

        return new PageImpl<>(list, pageable, total);
    }

    public Page<_student> findByClazzId(Pageable pageable, Long clazzId) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber() - 1) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_student> list = studentDao.selectByClazzId(clazzId, options);
        int total = studentDao.countByClazzId(clazzId);

        return new PageImpl<>(list, pageable, total);
    }

    public Page<_student> findBySchoolId(Pageable pageable, Long schoolId) {

        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber() - 1) * limit;

        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);
        List<_student> list = studentDao.selectBySchoolId(schoolId, options);
        int total = studentDao.countBySchoolId(schoolId);

        return new PageImpl<>(list, pageable, total);
    }


    public boolean delete(Long id) {
        _student student = Optional.ofNullable(studentDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found studentId+" + id));
        student.setStatus(_enum.StatusStudentEnum.OFF.value);
        return studentDao.update(student) == 1;
    }


    public boolean update(Long id, StudentReq studentReq) {
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
        return studentDao.update(clazz)==1;
    }

}
