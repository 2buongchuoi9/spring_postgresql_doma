package com.den.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.den.entity._clazz;
import com.den.model.request.ClazzReq;
import com.den.service.ClazzService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/class")
@Tag(name = "class controller",description = "used criteria ")
public class ClazzController {

  private final ClazzService clazzService;

  public ClazzController(ClazzService clazzService) {
    this.clazzService = clazzService;
  }

  @Operation(summary = "add class")
  @PostMapping("")
  public ResponseEntity<_clazz> add(@RequestBody @Valid ClazzReq clazzReq) {
    return ResponseEntity.ok().body(clazzService.add(clazzReq));
  }

  @Operation(summary = "get all class")
  @GetMapping("")
  public ResponseEntity<List<_clazz>> getAll() {
    return ResponseEntity.ok().body(clazzService.findAll());
  }

  @Operation(summary = "get one class")
  @GetMapping("/{id}")
  public ResponseEntity<_clazz> findOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(clazzService.findById(id));
  }

  @Operation(summary = "delete class")
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    return ResponseEntity.ok().body(clazzService.delete(id));
  }

  @Operation(summary = "update class")
  @PostMapping("/{id}")
  public ResponseEntity<_clazz> update(@PathVariable Long id, @RequestBody @Valid ClazzReq clazzReq) {
    return ResponseEntity.ok().body(clazzService.update(id, clazzReq));
  }
}
