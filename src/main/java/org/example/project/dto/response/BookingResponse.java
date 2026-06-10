package org.example.project.dto.response;


import lombok.*;
import org.example.project.entity.Booking;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponse {
    private Long bookingId;
    private String courtName;
    private String customerName;
    private String slot;
    private String status;
    public static BookingResponse fromEntity(Booking booking){
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .courtName(booking.getCourt().getName())
                .customerName(booking.getCustomer().getUsername())
                .slot(booking.getTimeSlot().getStartTime() + " - " + booking.getTimeSlot().getEndTime())
                .status(booking.getStatus().name())
                .build();
    }
}

