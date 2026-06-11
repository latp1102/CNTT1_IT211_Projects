package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.CourtRequest;
import org.example.project.dto.response.*;
import org.example.project.service.CourtService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager/courts")
@RequiredArgsConstructor
public class CourtController {
    private final CourtService courtService;
    @PostMapping
    public ResponseEntity<ResponseDTO<CourtResponse>> create(@RequestBody CourtRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseDTO.<CourtResponse>builder()
                                .success(true)
                                .message("Court created")
                                .data(courtService.create(request))
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CourtResponse>>> getAll(){
        return ResponseEntity.ok(
                ResponseDTO.<List<CourtResponse>>builder()
                        .success(true)
                        .message("Court list")
                        .data(courtService.findAll())
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CourtResponse>> getById(@PathVariable Long id){
        return ResponseEntity.ok(
                ResponseDTO.<CourtResponse>builder()
                        .success(true)
                        .message("Court found")
                        .data(courtService.findById(id))
                        .build()
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CourtResponse>> update(@PathVariable Long id, @RequestBody CourtRequest request) {
        return ResponseEntity.ok(
                ResponseDTO.<CourtResponse>builder().success(true)
                        .message("Court updated")
                        .data(courtService.update(id, request))
                        .build()
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id){
        courtService.delete(id);
        return ResponseEntity.ok(
                ResponseDTO.<Void>builder()
                        .success(true)
                        .message("Court deleted")
                        .build()
        );
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<List<CourtResponse>>> search(@RequestParam String keyword){
        return ResponseEntity.ok(
                ResponseDTO.<List<CourtResponse>>builder()
                        .success(true)
                        .message("Search results")
                        .data(courtService.search(keyword))
                        .build()
        );
    }
}
