package com.den.controller;

import com.den.entity._clazz;
import com.den.model.request.ClazzReq;
import com.den.service.ClazzServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/class")
@Tag(name = "class controller version 2",description = "used SQL template")
public class ClazzControllerV2 {

    private final ClazzServiceV2 clazzServiceV2;

    public ClazzControllerV2(ClazzServiceV2 clazzServiceV2) {
        this.clazzServiceV2 = clazzServiceV2;
    }

    @Operation(summary = "add class")
    @PostMapping("")
    public ResponseEntity<Boolean> add(@RequestBody @Valid ClazzReq clazzReq) {
        return ResponseEntity.ok().body(clazzServiceV2.add(clazzReq));
    }

    @Operation(summary = "get all class")
    @GetMapping("")
    public ResponseEntity<Page<_clazz>> getAll(
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(clazzServiceV2.findAll(PageRequest.of(page, limit)));
    }

    @Operation(summary = "get one class")
    @GetMapping("/{id}")
    public ResponseEntity<_clazz> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(clazzServiceV2.findById(id));
    }

    @Operation(summary = "delete class")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(clazzServiceV2.delete(id));
    }

    @Operation(summary = "update class")
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @RequestBody @Valid ClazzReq clazzReq) {
        return ResponseEntity.ok().body(clazzServiceV2.update(id, clazzReq));
    }

}
