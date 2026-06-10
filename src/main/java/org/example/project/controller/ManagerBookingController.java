package org.example.project.controller;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.response.BookingResponse;
import org.example.project.dto.response.ResponseDTO;
import org.example.project.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager/bookings")
@RequiredArgsConstructor
public class ManagerBookingController {
    private final BookingService bookingService;
    @PutMapping("{id}/approve")
    public ResponseEntity<ResponseDTO<BookingResponse>> approve(@PathVariable Long id){
        return ResponseEntity.ok(
                ResponseDTO.<BookingResponse>builder()
                        .success(true)
                        .message("Approved")
                        .data(bookingService.approveBooking(id))
                        .build()
        );
    }
    @PutMapping("{id}/reject")
    public ResponseEntity<ResponseDTO<BookingResponse>> reject(@PathVariable Long id){
        return ResponseEntity.ok(
                ResponseDTO.<BookingResponse>builder()
                        .success(true)
                        .message("Rejected")
                        .data(bookingService.rejectBooking(id))
                        .build()
        );
    }
}
