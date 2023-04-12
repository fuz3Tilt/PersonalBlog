package ru.kradin.blog.services.implementations;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.RegistrationService;
import ru.kradin.blog.services.interfaces.UserVerificationService;

import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserVerificationService userVerificationService;

    @Override
    @Transactional
    public void register(User user) throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailVerified(false);
        user.setAccountNonLocked(true);
        user.setRole(Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        userVerificationService.sendVerificationEmail(userRepository.findByUsername(user.getUsername()).get());
    }
}
