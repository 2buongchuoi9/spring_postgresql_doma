package com.den.service;

import com.den.dao.ImageDao;
import com.den.entity._image;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    public final ImageDao imageDao;

    public ImageService(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public void add (_image image){
        if(Optional.ofNullable(imageDao.selectByLink(image.getLink())).isEmpty()) imageDao.insert(image);
    }

    public Page<_image> findAll (Pageable pageable){
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber()-1)*limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_image> list = imageDao.selectAll(options);
        int total = imageDao.countAll();

        return new PageImpl<>(list, pageable, total);
    }

}
