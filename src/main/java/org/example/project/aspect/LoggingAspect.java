package org.example.project.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.*;
import org.example.project.dto.response.BookingResponse;
import org.example.project.entity.AuditLog;
import org.example.project.repository.AuditLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {
    private final AuditLogRepository auditLogRepository;
    @AfterReturning(pointcut = "execution(* org.example.project.service.BookingService.createBooking(..))", returning = "result")
    public void bookingSuccess(Object result){
        BookingResponse response = (BookingResponse) result;
        AuditLog log =
                AuditLog.builder()
                        .username(response.getCustomerName())
                        .action("BOOKING COURT " + response.getCourtName())
                        .status("SUCCESS")
                        .createdAt(LocalDateTime.now())
                        .build();
        auditLogRepository.save(log);
    }
    @AfterThrowing(pointcut = "execution(* org.example.project.service.BookingService.createBooking(..))", throwing = "exception")
    public void bookingFail(Exception exception){
        AuditLog log = AuditLog.builder().username("UNKNOWN")
                .action(exception.getMessage())
                .status("FAILED")
                .createdAt(LocalDateTime.now())
                .build();
        auditLogRepository.save(log);
    }
}