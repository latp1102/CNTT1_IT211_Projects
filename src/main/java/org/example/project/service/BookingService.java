package org.example.project.service;

import lombok.RequiredArgsConstructor;
import org.example.project.dto.request.BookingRequest;
import org.example.project.dto.response.BookingResponse;
import org.example.project.entity.*;
import org.example.project.exception.BookingConflictException;
import org.example.project.repository.BookingRepository;
import org.example.project.repository.CourtRepository;
import org.example.project.repository.TimeSlotRepository;
import org.example.project.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CourtRepository courtRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    public BookingResponse createBooking(BookingRequest request){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("không tìm thấy user"));
        Court court = courtRepository.findById(request.getCourtId()).orElseThrow(() -> new RuntimeException("không tìm thấy sân"));
        TimeSlot timeSlot = timeSlotRepository.findById(request.getTimeSlotId()).orElseThrow(() -> new RuntimeException("không tìm thấy thời gian"));
        boolean exists = bookingRepository
                .existsByCourtAndBookingDateAndTimeSlotAndStatusIn(
                        court,
                        request.getBookingDate(),
                        timeSlot,
                        List.of(
                                BookingStatus.PENDING,
//                              BookingStatus.CONFIRMED,
                                BookingStatus.APPROVED
                        )
                );
        if(exists){
                throw new BookingConflictException("Court đã đặt lịch");
        }
        Booking booking = Booking.builder()
                .court(court)
                .customer(customer)
                .timeSlot(timeSlot)
                .bookingDate(request.getBookingDate())
                .status(BookingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);
        return BookingResponse.fromEntity(booking);
    }
    public List<BookingResponse> myBooking(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User customer = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("không tìm thấy user"));
        return bookingRepository.findByCustomer(customer).stream().map(BookingResponse::fromEntity).toList();
    }
    //t
    public BookingResponse approveBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("không tìm thấy booking"));
        booking.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking);
        return BookingResponse.fromEntity(booking);
    }
    //d
    public BookingResponse rejectBooking(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("không tìm thấy booking"));
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
        return BookingResponse.fromEntity(booking);
    }
}
