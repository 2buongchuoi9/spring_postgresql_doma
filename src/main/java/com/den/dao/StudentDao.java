package com.den.dao;

import com.den.entity._student;
import org.seasar.doma.*;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Dao
@ConfigAutowireable
public interface StudentDao {

    @Insert
    int insert(_student student);

    @Update
    int update(_student student);

    @Delete(sqlFile = true)
    int delete(Long id);

    @Select
    _student selectByEmail(String email);

    @Select
    List<_student> selectAll();

    @Select
    List<_student> selectAll(SelectOptions options);

    @Select
    _student selectById(Long id);

    @Select
    List<_student> selectByClazzId(Long clazzId, SelectOptions options);

    @Select
    int countByClazzId(Long clazzId);

    @Select
    int countAll();

    @Select
    List<_student> selectByKeySearch(String keySearch, SelectOptions options);

    @Select
    int countByKeySearch(String keySearch);

    @Select
    List<_student> selectBySchoolId(Long schoolId, SelectOptions options);

    @Select
    int countBySchoolId(Long schoolId);

    @Select
    _student selectByEmailNotId(String email, Long id);

}
