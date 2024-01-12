package com.den.controller;

import com.den.entity._image;
import com.den.exceptions.UnKnowError;
import com.den.service.CloudinaryService;
import com.den.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/file")
public class FileController {
    public final CloudinaryService cloudinaryService;
    public final ImageService imageService;

    public FileController(CloudinaryService cloudinaryService, ImageService imageService) {
        this.cloudinaryService = cloudinaryService;
        this.imageService = imageService;
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile multipartFile) {
        try {
            String link = cloudinaryService.upload(multipartFile);
            System.out.println("::::::::::"+link);
            imageService.add(new _image(link));

            return ResponseEntity.ok().body(link);
        } catch (Exception e) {
            throw new UnKnowError("upload image fail");
        }
    }
}
