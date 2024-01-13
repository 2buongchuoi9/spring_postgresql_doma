package com.den.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import com.den.entity._school;


@Dao
@ConfigAutowireable
public interface SchoolDao {

  @Select
  List<_school> selectAll(SelectOptions options);

  @Select
  int countAll(SelectOptions options);

  @Insert
  int insert(_school school);

  @Select
  _school findById(Long id);

  @Update
  int update(_school school);

  @Select
  _school selectByName(String name);


}
