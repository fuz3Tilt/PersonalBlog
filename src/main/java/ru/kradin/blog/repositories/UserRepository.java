package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}