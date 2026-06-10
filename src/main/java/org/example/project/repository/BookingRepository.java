package org.example.project.repository;

import org.example.project.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(User customer);
    boolean existsByCourtAndBookingDateAndTimeSlotAndStatus(Court court, LocalDate bookingDate, TimeSlot timeSlot, List<BookingStatus> statuses);

    boolean existsByCourtAndBookingDateAndTimeSlotAndStatusIn(Court court, LocalDate bookingDate, TimeSlot timeSlot, Collection<BookingStatus> statuses);
}
