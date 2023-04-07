package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.enums.TokenPurpose;
import ru.kradin.blog.models.User;
import ru.kradin.blog.models.UserVerificationToken;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {
    Optional<UserVerificationToken> findByTokenAndTokenPurpose(String token, TokenPurpose tokenPurpose);

    Optional<UserVerificationToken> findByUserAndTokenPurpose(User user, TokenPurpose tokenPurpose);

    void deleteByExpiryDateLessThan(LocalDateTime now);
}