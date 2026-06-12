package org.example.project.config.data;

import lombok.RequiredArgsConstructor;
import org.example.project.entity.Court;
import org.example.project.entity.Role;
import org.example.project.entity.TimeSlot;
import org.example.project.entity.User;
import org.example.project.repository.CourtRepository;
import org.example.project.repository.TimeSlotRepository;
import org.example.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class dataInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CourtRepository courtRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initUsers();
        initCourts();
        initTimeSlots();
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Admin User")
                    .role(Role.ROLE_ADMIN)
                    .enabled(true)
                    .build();

            User manager = User.builder()
                    .username("manager")
                    .email("manager@example.com")
                    .password(passwordEncoder.encode("manager123"))
                    .fullName("Manager User")
                    .role(Role.ROLE_MANAGER)
                    .enabled(true)
                    .build();

            User customer = User.builder()
                    .username("customer")
                    .email("customer@example.com")
                    .password(passwordEncoder.encode("customer123"))
                    .fullName("Customer User")
                    .role(Role.ROLE_CUSTOMER)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            userRepository.save(manager);
            userRepository.save(customer);
        }
    }

    private void initCourts() {
        if (courtRepository.count() == 0) {
            Court court1 = Court.builder()
                    .name("Sân bóng 1")
                    .address("123 Đường ABC, Quận 1, TP.HCM")
                    .price(new BigDecimal("500000"))
                    .imageUrl("https://example.com/court1.jpg")
                    .build();

            Court court2 = Court.builder()
                    .name("Sân bóng 2")
                    .address("456 Đường XYZ, Quận 2, TP.HCM")
                    .price(new BigDecimal("600000"))
                    .imageUrl("https://example.com/court2.jpg")
                    .build();

            Court court3 = Court.builder()
                    .name("Sân bóng 3")
                    .address("789 Đường DEF, Quận 3, TP.HCM")
                    .price(new BigDecimal("550000"))
                    .imageUrl("https://example.com/court3.jpg")
                    .build();

            courtRepository.save(court1);
            courtRepository.save(court2);
            courtRepository.save(court3);
        }
    }

    private void initTimeSlots() {
        if (timeSlotRepository.count() == 0) {
            TimeSlot slot1 = TimeSlot.builder()
                    .startTime(LocalTime.of(7, 0))
                    .endTime(LocalTime.of(9, 0))
                    .build();

            TimeSlot slot2 = TimeSlot.builder()
                    .startTime(LocalTime.of(9, 0))
                    .endTime(LocalTime.of(11, 0))
                    .build();

            TimeSlot slot3 = TimeSlot.builder()
                    .startTime(LocalTime.of(14, 0))
                    .endTime(LocalTime.of(16, 0))
                    .build();

            TimeSlot slot4 = TimeSlot.builder()
                    .startTime(LocalTime.of(16, 0))
                    .endTime(LocalTime.of(18, 0))
                    .build();

            TimeSlot slot5 = TimeSlot.builder()
                    .startTime(LocalTime.of(18, 0))
                    .endTime(LocalTime.of(20, 0))
                    .build();

            TimeSlot slot6 = TimeSlot.builder()
                    .startTime(LocalTime.of(20, 0))
                    .endTime(LocalTime.of(22, 0))
                    .build();

            timeSlotRepository.save(slot1);
            timeSlotRepository.save(slot2);
            timeSlotRepository.save(slot3);
            timeSlotRepository.save(slot4);
            timeSlotRepository.save(slot5);
            timeSlotRepository.save(slot6);
        }
    }
}
