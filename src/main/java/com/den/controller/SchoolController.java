package com.den.controller;

import com.den.entity._school;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.List;

@RestController
@RequestMapping("/school")
@Tag(name = "school controller",description = "used criteria")
public class SchoolController {
  private final SchoolService schoolService;

  public SchoolController(SchoolService schoolService) {
    this.schoolService = schoolService;
  }

  @Operation(summary = "add school")
  @PostMapping("")
  public ResponseEntity<_school> add(@RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolService.add(schoolReq));
  }

  @Operation(summary = "get all school")
  @GetMapping("")
  public ResponseEntity<List<_school>> getAll() {
    return ResponseEntity.ok().body(schoolService.findAll());
  }

  @Operation(summary = "get one school")
  @GetMapping("/{id}")
  public ResponseEntity<_school> getOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(schoolService.findById(id));
  }

  @Operation(summary = "update school")
  @PostMapping("/{id}")
  public ResponseEntity<_school> update(@PathVariable Long id, @RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolService.update(id, schoolReq));
  }

}
