package com.den.service;

import com.den.dao.ImageDao;
import com.den.entity._image;
import com.den.exceptions.NotFoundError;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageService {
    private final ImageDao imageDao;
    private final CloudinaryService cloudinaryService;

    public ImageService(ImageDao imageDao, CloudinaryService cloudinaryService) {
        this.imageDao = imageDao;
        this.cloudinaryService = cloudinaryService;
    }

    public _image addImageAndFile(MultipartFile multipartFile) {
        _image image = new _image();
        Map uploadResult = cloudinaryService.uploadV2(multipartFile);
        if (uploadResult != null) {
            image.setPublicId(uploadResult.get("public_id").toString());
            image.setUrl(uploadResult.get("url").toString());
            imageDao.insert(image);
        }
        return image;
    }

    public Page<_image> findAll(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = (pageable.getPageNumber() - 1) * limit;
        SelectOptions options = SelectOptions.get().limit(limit).offset(offset);

        List<_image> list = imageDao.selectAll(options);
        int total = imageDao.countAll();

        return new PageImpl<>(list, pageable, total);
    }

    public boolean delete(Long id) {
        _image image = Optional.ofNullable(imageDao.selectById(id)).orElseThrow(() -> new NotFoundError("not found imageId=" + id));
        if (image.getPublicId() != null) cloudinaryService.delete(image.getPublicId());
        return imageDao.delete(image) == 1;
    }

}
