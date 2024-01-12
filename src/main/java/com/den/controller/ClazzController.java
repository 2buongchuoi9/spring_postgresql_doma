package com.den.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.den.entity._clazz;
import com.den.exceptions.NotFoundError;
import com.den.model.request.ClazzReq;
import com.den.service.ClazzService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/class")
public class ClazzController {

  @Autowired
  private ClazzService clazzService;

  // add
  @PostMapping("")
  public ResponseEntity<?> add(@RequestBody @Valid ClazzReq clazzReq) {
    return ResponseEntity.ok().body(clazzService.add(clazzReq));
  }

  // get all
  @GetMapping("")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(clazzService.findAll());
  }

  // get one
  @GetMapping("/{id}")
  public ResponseEntity<?> findOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(clazzService.findById(id));
  }

  // delete
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    return ResponseEntity.ok().body(clazzService.delete(id));
  }

  // update
  @PostMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClazzReq clazzReq) {
    return ResponseEntity.ok().body(clazzService.update(id, clazzReq));
  }

}
