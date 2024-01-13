package com.den.controller;

import com.den.entity._student;
import com.den.model.request.StudentReq;
import com.den.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Tag(name = "student controller", description = "used criteria")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(summary = "add student")
    @PostMapping("")
    public ResponseEntity<_student> add(@RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentService.add(studentReq));
    }

    @Operation(summary = "get all student")
    @GetMapping("")
    public ResponseEntity<Page<_student>> getAll(
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<_student> list = studentService.findAll(PageRequest.of(page, limit));
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "search student")
    @GetMapping("/search/{keySearch}")
    public ResponseEntity<Page<_student>> search(
            @PathVariable String keySearch,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<_student> list = studentService.search(PageRequest.of(page, limit), keySearch);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "get one student")
    @GetMapping("/{id}")
    public ResponseEntity<_student> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.findById(id));
    }

    @Operation(summary = "get all student by classId")
    @GetMapping("/class/{clazzId}")
    public ResponseEntity<Page<_student>> findByClazzId(@PathVariable Long clazzId,
                                           @RequestParam(name = "limit", defaultValue = "5") int limit,
                                           @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentService.findByClazzId(PageRequest.of(page, limit), clazzId));
    }

    @Operation(summary = "get all student by schoolId")
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<Page<_student>> findBySchoolId(@PathVariable Long schoolId,
                                            @RequestParam(name = "limit", defaultValue = "5") int limit,
                                            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentService.findBySchoolId(PageRequest.of(page, limit), schoolId));
    }

    @Operation(summary = "delete student")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.delete(id));
    }

    @Operation(summary = "update student")
    @PostMapping("/{id}")
    public ResponseEntity<_student> update(@PathVariable Long id, @RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentService.update(id, studentReq));
    }

}
