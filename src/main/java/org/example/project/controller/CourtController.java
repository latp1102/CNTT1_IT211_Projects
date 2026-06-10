package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.CourtrRequest;
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
    public ResponseEntity<ResponseDTO<CourtResponse>>
    create(
            @RequestBody CourtrRequest request
    ){

        return ResponseEntity.status(
                        HttpStatus.CREATED
                )
                .body(
                        ResponseDTO.<CourtResponse>builder()
                                .success(true)
                                .message("Court created")
                                .data(
                                        courtService.create(request)
                                )
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<CourtResponse>>>
    getAll(){

        return ResponseEntity.ok(
                ResponseDTO.<List<CourtResponse>>builder()
                        .success(true)
                        .message("Court list")
                        .data(
                                courtService.findAll()
                        )
                        .build()
        );
    }
}
