package com.den.dao;

import com.den.entity._clazz;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ClazzDao {

    @Insert
    int insert(_clazz clazz);

    @Select
    List<_clazz> selectAll(SelectOptions options);

    @Update
    int update(_clazz clazz);

    @Select
    _clazz selectById(Long id);

    @Select
    _clazz selectByCode(String code);

    @Select
    int countAll(SelectOptions options);


    @Select
    _clazz selectByCodeNotId(String code, Long id);

    @Select
    int countById(Long clazzId);
}
