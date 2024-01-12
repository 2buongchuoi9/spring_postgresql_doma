package com.den.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.den.entity._clazz;
import com.den.exceptions.NotFoundError;
import com.den.model.request.ClazzReq;
import com.den.model.request.StudentReq;
import com.den.service.StudentService;

import jakarta.validation.Valid;

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
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(studentService.findAll());
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
