package com.den.controller;

import com.den.entity._student;
import com.den.model.request.StudentReq;
import com.den.model.request.StudentUpdateManyReq;
import com.den.model.response.CustomPage;
import com.den.model.response.MainRes;
import com.den.service.ExcelUploadService;
import com.den.service.StudentServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v2/student")
@Tag(description = "used SQL templates, date format is dd-MM-yyyy", name = "student v2 controller")
public class StudentControllerV2 {

    private final StudentServiceV2 studentServiceV2;

    public StudentControllerV2(StudentServiceV2 studentServiceV2, ExcelUploadService excelUploadService) {
        this.studentServiceV2 = studentServiceV2;
    }

    @Operation(summary = "add student")
    @PostMapping("")
    public ResponseEntity<_student> add(@RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.add(studentReq));
    }

    @Operation(summary = "get all student ")
    @GetMapping("")
    public ResponseEntity<CustomPage<_student>> getAll(
            @RequestParam(name = "limit", defaultValue = "5", required = false) Integer limit,
            @RequestParam(name = "page", defaultValue = "1",  required = false) Integer page,
            @RequestParam(name = "status", required = false) Integer status) {



//        if(limit !=null && page !=null)

        CustomPage<_student> result = new CustomPage<_student>(studentServiceV2.findAll(PageRequest.of(page - 1, limit), status));
        System.out.println(result);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "search student for key search")
    @GetMapping("/search/{keySearch}")
    public ResponseEntity<CustomPage<_student>> search(@PathVariable String keySearch, @RequestParam(name = "limit", defaultValue = "5") int limit, @RequestParam(name = "page", defaultValue = "1") int page) {

        CustomPage<_student> result = new CustomPage<>(studentServiceV2.search(PageRequest.of(page - 1, limit), keySearch));
        return ResponseEntity.ok().body(result);
    }


    @Operation(summary = "get one student ")
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.findById(id));
    }

    @Operation(summary = "get all student by clazzId ")
    @GetMapping("/class/{clazzId}")
    public ResponseEntity<CustomPage<_student>> findByClazzId(
            @PathVariable Long clazzId,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "status", required = false) Integer status) {
        return ResponseEntity.ok().body(new CustomPage<_student>(studentServiceV2.findByClazzId(PageRequest.of(page - 1, limit), clazzId,status)));
    }

    @Operation(summary = "get all student by schoolId")
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<CustomPage<_student>> findBySchoolId(
            @PathVariable Long schoolId,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "status", required = false) Integer status) {
        return ResponseEntity.ok().body(new CustomPage<_student>(studentServiceV2.findBySchoolId(PageRequest.of(page - 1, limit), schoolId,status)));
    }

    @Operation(summary = "delete student")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.delete(id));
    }

    @Operation(summary = "update student")
    @PostMapping("/{id}")
    public ResponseEntity<_student> update(@PathVariable Long id, @RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.update(id, studentReq));
    }


    @Operation(summary = "update many student just colum(status, clazzId), return list id is fail to update, null if all success")
    @PostMapping("/update-many")
    public ResponseEntity<Boolean> update(@RequestBody @Valid StudentUpdateManyReq studentUpdateManyReq) {
        return ResponseEntity.ok().body(studentServiceV2.updateMany(studentUpdateManyReq));
    }


}
