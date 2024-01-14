package com.den.controller;

import com.den.model.request.SchoolReq;
import com.den.service.SchoolServiceV2;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/school")
public class SchoolControllerV2 {

  final
  SchoolServiceV2 schoolServiceV2;

  public SchoolControllerV2(SchoolServiceV2 schoolServiceV2) {
    this.schoolServiceV2 = schoolServiceV2;
  }

//  add
  @PostMapping("")
  public ResponseEntity<?> add(@RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolServiceV2.add(schoolReq));
  }
// get all
  @GetMapping("")
  public ResponseEntity<?> getAll(
      @RequestParam(name = "limit", defaultValue = "5") int limit,
      @RequestParam(name = "page", defaultValue = "1") int page) {
    return ResponseEntity.ok().body(schoolServiceV2.findAll(PageRequest.of(page, limit)));
  }

//  find by id
  @GetMapping("/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(schoolServiceV2.findById(id));
  }

   @PostMapping("/{id}")
   public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid
   SchoolReq schoolReq) {
   return ResponseEntity.ok().body(schoolServiceV2.update(id, schoolReq));
   }

}
