package com.den.dao;

import com.den.entity._image;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.SelectOptions;

import java.util.List;

@Dao
@ConfigAutowireable
public interface ImageDao {
    @Insert
    int insert(_image image);

    @Delete
    int delete(_image image);

    @Select
    List<_image> selectAll(SelectOptions options);

    @Select
    int countAll();

    @Select
    _image selectById(Long id);
}
