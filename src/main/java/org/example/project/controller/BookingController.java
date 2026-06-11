package org.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.BookingRequest;
import org.example.project.dto.response.BookingResponse;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @PostMapping
    public ResponseEntity<ResponseDTO<BookingResponse>> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(ResponseDTO.<BookingResponse>builder()
                .success(true)
                .message("Đặt phòng thành công")
                .data(bookingService.createBooking(request))
                .build());
    }
    @GetMapping("/history")
    public ResponseEntity<ResponseDTO<List<BookingResponse>>> history() {
        return ResponseEntity.ok(ResponseDTO.<List<BookingResponse>>builder()
                .success(true)
                .message("Lịch sử đặt phòng")
                .data(bookingService.myBooking())
                .build());
    }
    @GetMapping
    public ResponseEntity<ResponseDTO<List<BookingResponse>>> getAllBookings() {
        return ResponseEntity.ok(ResponseDTO.<List<BookingResponse>>builder()
                .success(true)
                .message("Danh sách đặt phòng")
                .data(bookingService.getAllBookings())
                .build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<BookingResponse>> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(ResponseDTO.<BookingResponse>builder()
                .success(true)
                .message("Cập nhật đặt phòng thành công")
                .data(bookingService.updateBooking(id, request))
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> cancelBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok(ResponseDTO.<Void>builder()
                .success(true)
                .message("Hủy đặt phòng thành công")
                .build());
        }
}
