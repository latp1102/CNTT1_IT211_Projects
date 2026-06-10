package org.example.project.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.project.repository.TokenBlackistRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TokenCleanupJob {
    private final TokenBlackistRepository tokenBlackistRepository;
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanup(){
        tokenBlackistRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }
}
