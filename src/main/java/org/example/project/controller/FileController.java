package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileUploadService fileUploadService;
    @PostMapping("/upload")
    public ResponseEntity<ResponseDTO<Map<String, String>>> uploadFile(@RequestParam("file") MultipartFile file){
        String url = fileUploadService.upload(file);
        return ResponseEntity.ok(ResponseDTO.<Map<String, String>>builder()
                .success(true)
                .message("Upload ảnh thành công")
                .data(Map.of("url", url))
                .build());
    }
}
