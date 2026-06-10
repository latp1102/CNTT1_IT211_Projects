package org.example.project.dto.request;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingRequest {
    private Long courtId;
    private Long timeSlotId;
    private LocalDate bookingDate;
}
