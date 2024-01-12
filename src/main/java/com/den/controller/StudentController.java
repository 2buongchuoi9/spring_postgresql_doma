package com.den.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.den.entity._clazz;
import com.den.entity._student;
import com.den.exceptions.NotFoundError;
import com.den.model.request.ClazzReq;
import com.den.model.request.StudentReq;
import com.den.model.response.MainRes;
import com.den.service.StudentService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.seasar.doma.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/student")
public class StudentController {

  @Autowired
  private StudentService studentService;

  // add
  @PostMapping("")
  public ResponseEntity<?> add(@RequestBody @Valid StudentReq studentReq) {
    return ResponseEntity.ok().body(studentService.add(studentReq));
  }

  // get all
  @GetMapping("")
  public ResponseEntity<?> getAll(
      @RequestParam(name = "limit", defaultValue = "5") int limit,
      @RequestParam(name = "page", defaultValue = "1") int page) {

    List<_student> list = studentService.findAll();
    return ResponseEntity.ok().body(new MainRes(Map.of("list", list, "count", list.size())));
  }

  // get all
  @PostMapping("/search/{keySearch}")
  public ResponseEntity<?> search(
      @PathVariable String keySearch,
      @RequestParam(name = "limit", defaultValue = "5") int limit,
      @RequestParam(name = "page", defaultValue = "1") int page) {

    List<_student> list = studentService.findAll();
    return ResponseEntity.ok().body(new MainRes(Map.of("list", list, "count", list.size())));
  }

  // get one
  @GetMapping("/{id}")
  public ResponseEntity<?> findOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(studentService.findById(id));
  }

  // delete
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    return ResponseEntity.ok().body(studentService.delete(id));
  }

  // update
  @PostMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid StudentReq studentReq) {
    return ResponseEntity.ok().body(studentService.update(id, studentReq));
  }

}
