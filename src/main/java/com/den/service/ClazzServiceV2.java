package com.den.service;

import com.den.dao.ClazzDao;
import com.den.entity._clazz;
import com.den.exceptions.DuplicateRecordError;
import com.den.exceptions.NotFoundError;
import com.den.model.request.ClazzReq;
import org.modelmapper.ModelMapper;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClazzServiceV2 {
    private final ClazzDao clazzDao;
    private final ModelMapper mapper;

    public ClazzServiceV2(ClazzDao clazzDao, ModelMapper mapper) {
        this.clazzDao = clazzDao;
        this.mapper = mapper;
    }

    public boolean add(ClazzReq clazzReq) {

        Optional.ofNullable(clazzDao.selectByCode(clazzReq.getCode())).ifPresent(v->{
            throw new DuplicateRecordError("duplicate code: " + clazzReq.getCode());
        });

        _clazz clazz = mapper.map(clazzReq, _clazz.class);
        return clazzDao.insert(clazz) == 1;
    }

    public Page<_clazz> findAll(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber()  * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);
        List<_clazz> list = clazzDao.selectAll(options);
        int total = clazzDao.countAll(options);
        return new PageImpl<>(list, pageable, total);
    }

    public _clazz findById(Long id) {
        return Optional.ofNullable(clazzDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found clazz id=" + id));
    }

    public boolean update(Long id, ClazzReq clazzReq) {
        Optional.ofNullable(clazzDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found clazz id=" + id));

//      check name
        Optional.ofNullable(clazzDao.selectByCodeNotId(clazzReq.getCode(),id)).ifPresent(v -> {
            throw new DuplicateRecordError("duplicate code="+clazzReq.getCode());
        });

        _clazz clazz = mapper.map(clazzReq, _clazz.class);
        clazz.setId(id);
        return clazzDao.update(clazz) == 1;
    }

    public boolean delete(Long id) {
        _clazz clazz = Optional.ofNullable(clazzDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found id=" + id));
        clazz.setStatus(false);
        return clazzDao.update(clazz) == 1;
    }
}
