package com.den.controller;

import com.den.entity._image;
import com.den.entity._student;
import com.den.exceptions.BabRequestError;
import com.den.exceptions.UnKnowError;
import com.den.model.ObjectResultExcel;
import com.den.model.response.CustomPage;
import com.den.model.response.MainRes;
import com.den.service.CloudinaryService;
import com.den.service.ExcelUploadService;
import com.den.service.ImageService;
import com.den.service.StudentServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v2/file")
@Tag(name = "File controller", description = "upload image, excel,...")
public class FileController {
    public final CloudinaryService cloudinaryService;
    public final ImageService imageService;
    public final ExcelUploadService excelUploadService;
    public final StudentServiceV2 studentServiceV2;

    public FileController(CloudinaryService cloudinaryService, ImageService imageService, ExcelUploadService excelUploadService, StudentServiceV2 studentServiceV2) {
        this.cloudinaryService = cloudinaryService;
        this.imageService = imageService;
        this.excelUploadService = excelUploadService;
        this.studentServiceV2 = studentServiceV2;
    }

    @Operation(summary = "up load image")
    @PostMapping("/upload-image")
    public ResponseEntity<_image> upload(@RequestParam(name = "file") MultipartFile multipartFile) {
        return ResponseEntity.ok().body(imageService.addImageAndFile(multipartFile));
    } //    upload file excel

    @Operation(summary = "upload excel to insert or update list student, response is a file excel result")
    @PostMapping("/upload-excel")
    public ResponseEntity<InputStreamResource> uploadExcelInsertStudent(@RequestParam(name = "file") MultipartFile multipartFile) {
        if (!excelUploadService.isValidExcelFile(multipartFile))
            throw new BabRequestError("Please upload an excel file!");
        try {
            List<ObjectResultExcel> list = excelUploadService.getListResultFromExcel(multipartFile.getInputStream());

            InputStreamResource inputStreamResource = excelUploadService.writeExcel(list);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "result.xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(inputStreamResource);

        } catch (Exception e) {
            e.printStackTrace();
         throw new UnKnowError("err");
        }
    }

    @Operation(summary = "get all image")
    @GetMapping("/image")
    public ResponseEntity<CustomPage<_image>> getAllImage(
            @RequestParam(name = "limit", defaultValue = "20") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(new CustomPage<_image>(imageService.findAll(PageRequest.of(page -1, limit))));
    }

    @Operation(summary = "delete image")
    @DeleteMapping("/image/{id}")
    public ResponseEntity<Boolean> deleteImage(@PathVariable Long id) {
        return ResponseEntity.ok().body(imageService.delete(id));
    }

    @Operation(summary = "export all student to file excel")
    @PostMapping("/export-excel")
    public ResponseEntity<InputStreamResource> exportExcel() {
        List<_student> list = studentServiceV2.findAll();
        InputStreamResource inputStreamResource = excelUploadService.exportStudent(list);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "result.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(inputStreamResource);
    }


}
