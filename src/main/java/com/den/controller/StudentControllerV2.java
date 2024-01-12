package com.den.controller;

import com.den.entity._student;
import com.den.model.request.StudentReq;
import com.den.model.response.MainRes;
import com.den.service.StudentServiceV2;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/student")
public class StudentControllerV2 {

    private final StudentServiceV2 studentServiceV2;

    public StudentControllerV2(StudentServiceV2 studentServiceV2) {
        this.studentServiceV2 = studentServiceV2;
    }

    // add
    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.add(studentReq));
    }

    // get all
    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam(name = "limit", defaultValue = "5") int limit, @RequestParam(name = "page", defaultValue = "1") int page) {

        Pageable p = PageRequest.of(page, limit);

        Page<_student> list = studentServiceV2.findAll(p);
        System.out.println("pageee::::" + p.getPageNumber());
        return ResponseEntity.ok().body(new MainRes(list));
    }

    // search
    @GetMapping("/search/{keySearch}")
    public ResponseEntity<?> search(@PathVariable String keySearch, @RequestParam(name = "limit", defaultValue = "5") int limit, @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<_student> list = studentServiceV2.search(PageRequest.of(page, limit), keySearch);
        return ResponseEntity.ok().body(new MainRes(list));
    }

    // get one
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.findById(id));
    }

    // find by clazzId
    @GetMapping("/class/{clazzId}")
    public ResponseEntity<?> findByClazzId(
            @PathVariable Long clazzId, @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentServiceV2.findByClazzId(PageRequest.of(page, limit), clazzId));
    }

    // find by schoolId
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<?> findBySchoolId(@PathVariable Long schoolId,
                                            @RequestParam(name = "limit", defaultValue = "5") int limit,
                                            @RequestParam(name = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok().body(studentServiceV2.findBySchoolId(PageRequest.of(page, limit), schoolId));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentServiceV2.delete(id));
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid StudentReq studentReq) {
        return ResponseEntity.ok().body(studentServiceV2.update(id, studentReq));
    }


}
