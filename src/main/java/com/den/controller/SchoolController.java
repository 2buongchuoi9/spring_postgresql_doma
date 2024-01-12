package com.den.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.den.model.request.SchoolReq;
import com.den.service.SchoolService;

import jakarta.validation.Valid;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/school")
public class SchoolController {
  private final SchoolService schoolService;

  public SchoolController(SchoolService schoolService) {
    this.schoolService = schoolService;
  }

  @PostMapping("")
  public ResponseEntity<?> add(@RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolService.add(schoolReq));
  }

  @GetMapping("")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(schoolService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(schoolService.findById(id));
  }

  @PostMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolService.update(id, schoolReq));
  }

}
