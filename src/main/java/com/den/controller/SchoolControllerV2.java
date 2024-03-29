package com.den.controller;

import com.den.entity._school;
import com.den.model.request.SchoolReq;
import com.den.model.response.CustomPage;
import com.den.service.SchoolServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/school")
@Tag(name = "school controller version 2",description = "used SQL template")
public class SchoolControllerV2 {

  final
  SchoolServiceV2 schoolServiceV2;

  public SchoolControllerV2(SchoolServiceV2 schoolServiceV2) {
    this.schoolServiceV2 = schoolServiceV2;
  }

    @Operation(summary = "add school")
  @PostMapping("")
  public ResponseEntity<Boolean> add(@RequestBody @Valid SchoolReq schoolReq) {
    return ResponseEntity.ok().body(schoolServiceV2.add(schoolReq));
  }
    @Operation(summary = "get all school")
  @GetMapping("")
  public ResponseEntity<CustomPage<_school>> getAll(
      @RequestParam(name = "limit", defaultValue = "5") int limit,
      @RequestParam(name = "page", defaultValue = "1") int page) {
    CustomPage<_school> result = new CustomPage<_school>(schoolServiceV2.findAll(PageRequest.of(page-1, limit)));
      System.out.println(result);
    return ResponseEntity.ok().body(result);
  }

    @Operation(summary = "get one school")
  @GetMapping("/{id}")
  public ResponseEntity<_school> getOne(@PathVariable Long id) {
    return ResponseEntity.ok().body(schoolServiceV2.findById(id));
  }

    @Operation(summary = "update school")
   @PostMapping("/{id}")
   public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody @Valid
   SchoolReq schoolReq) {
   return ResponseEntity.ok().body(schoolServiceV2.update(id, schoolReq));
   }

}
