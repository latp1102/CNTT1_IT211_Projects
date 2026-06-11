package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.UserCreateRequest;
import org.example.project.dto.response.UserResponse;
import org.example.project.dto.request.UserUpdateRequest;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<ResponseDTO<Page<UserResponse>>> searchUsers(
        @RequestParam(defaultValue = "0") String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(ResponseDTO.<Page<UserResponse>>builder().success(true).message("tìm kiếm người dùng thành công").data(userService.searchUsers(keyword, page, size)).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>> findById(@PathVariable Long id){
        return ResponseEntity.ok(ResponseDTO.<UserResponse>builder().success(true).message("tìm kiếm người dùng thành công").data(userService.findById(id)).build());
    }
    @PostMapping
    public ResponseEntity<ResponseDTO<UserResponse>>
    create(@Valid @RequestBody UserCreateRequest request){
        return new ResponseEntity<>(ResponseDTO.<UserResponse>builder().success(true).message("Thêm người dùng thành công").data(userService.create(request)).build(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<UserResponse>>
    update(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return new ResponseEntity<>(ResponseDTO.<UserResponse>builder().success(true).message("Cập nhật người dùng thành công").data(userService.update(id, request)).build(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Long id){
        userService.delete(id);
        return new ResponseEntity<>(ResponseDTO.<Void>builder().success(true).message("Xóa người dùng thành công").build(), HttpStatus.OK);
    }
}
