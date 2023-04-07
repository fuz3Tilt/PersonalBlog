package ru.kradin.blog.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kradin.blog.repositories.UserVerificationTokenRepository;

import java.time.LocalDateTime;

@Service
public class UserVerificationTokenServiceImpl {

    @Autowired
    private UserVerificationTokenRepository tokenRepository;

    @Scheduled(fixedRate = 1000*60*60*24, initialDelay = 1000)
    public void removeExpiredTokens() {
        tokenRepository.deleteByExpiryDateLessThan(LocalDateTime.now());
    }
}
