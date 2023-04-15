package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.UserRegistrationDTO;
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
    public void register(UserRegistrationDTO userRegistrationDTO) throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setEmail(userRegistrationDTO.getEmail());
        user.setEmailVerified(false);
        user.setAccountNonLocked(true);
        user.setRole(Role.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        userVerificationService.sendVerificationEmail(userRepository.findByUsername(user.getUsername()).get());
    }
}
