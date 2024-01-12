package com.den.controller;

import com.den.model.request.ClazzReq;
import com.den.service.ClazzServiceV2;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/class")
public class ClazzControllerV2 {

    private final ClazzServiceV2 clazzServiceV2;

    public ClazzControllerV2(ClazzServiceV2 clazzServiceV2) {
        this.clazzServiceV2 = clazzServiceV2;
    }

    // add
    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody @Valid ClazzReq clazzReq) {
        return ResponseEntity.ok().body(clazzServiceV2.add(clazzReq));
    }

    // get all
    @GetMapping("")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        return ResponseEntity.ok().body(clazzServiceV2.findAll(PageRequest.of(page, limit)));
    }

    // get one
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok().body(clazzServiceV2.findById(id));
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(clazzServiceV2.delete(id));
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClazzReq clazzReq) {
        return ResponseEntity.ok().body(clazzServiceV2.update(id, clazzReq));
    }

}
