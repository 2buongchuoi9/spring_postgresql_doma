package com.den.controller;

import com.den.entity._student;
import com.den.model.request.StudentReq;
import com.den.model.response.MainRes;
import com.den.service.ExcelUploadService;
import com.den.service.StudentServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> add(@RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.add(studentReq));
    }

    @Operation(summary = "get all student ")
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(name = "limit", defaultValue = "5") int limit, @RequestParam(name = "page", defaultValue = "1") int page) {

        Pageable p = PageRequest.of(page -1, limit);

        Page<_student> list = studentServiceV2.findAll(p);
        System.out.println("pageee::::" + p.getPageNumber());
        return ResponseEntity.ok().body(new MainRes(list));
    }

    @Operation(summary = "search student for key search")
    @GetMapping("/search/{keySearch}")
    public ResponseEntity<?> search(@PathVariable String keySearch, @RequestParam(name = "limit", defaultValue = "5") int limit, @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<_student> list = studentServiceV2.search(PageRequest.of(page-1, limit), keySearch);
        return ResponseEntity.ok().body(new MainRes(list));
    }


    @Operation(summary = "get one student ")
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.findById(id));
    }

    @Operation(summary = "get all student by clazzId ")
    @GetMapping("/class/{clazzId}")
    public ResponseEntity<?> findByClazzId(
            @PathVariable Long clazzId, @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentServiceV2.findByClazzId(PageRequest.of(page-1, limit), clazzId));
    }

    @Operation(summary = "get all student by schoolId")
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<?> findBySchoolId(@PathVariable Long schoolId,
                                            @RequestParam(name = "limit", defaultValue = "5") int limit,
                                            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentServiceV2.findBySchoolId(PageRequest.of(page-1, limit), schoolId));
    }

    @Operation(summary = "delete student")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.delete(id));
    }

    @Operation(summary = "update student")
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.update(id, studentReq));
    }


}
