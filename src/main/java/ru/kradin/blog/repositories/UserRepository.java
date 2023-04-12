package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByAccountNonLocked(boolean accountNonLocked);

    List<User> findByRole(Role role);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}