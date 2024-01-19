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

    @Update(sqlFile = true)
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
    int countAll();

    @Select
    List<_student> selectByKeySearch(String keySearch, SelectOptions options);

    @Select
    int countByKeySearch(String keySearch);

    @Select
    _student selectByEmailNotId(String email, Long id);

    @Select
    List<_student> selectByStatus(SelectOptions options, Integer status);

    @Select
    int countByStatus(Integer status);

    @Select
    List<_student> selectByClazzId(SelectOptions options, Long clazzId, Integer status);

    @Select
    int countByClazzId(Long clazzId, Integer status);

    @Select
    List<_student> selectBySchoolId(SelectOptions options, Long schoolId, Integer status);

    @Select
    int countBySchoolId(Long schoolId, Integer status);

    @BatchUpdate(include = {"status","clazzId"})
    int[] updateManySetStatusOrClazzId(List<_student> students);
}
