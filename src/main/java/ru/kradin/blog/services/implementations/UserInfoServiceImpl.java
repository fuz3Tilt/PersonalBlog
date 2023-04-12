package ru.kradin.blog.services.implementations;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;
import ru.kradin.blog.services.interfaces.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Override
    public User getUserInfo(Authentication authentication) {
        User user = authenticatedUserService.getCurentUser(authentication);
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional
    public void updateEmail(Authentication authentication, String email) {
        User user = authenticatedUserService.getCurentUser(authentication);

        user.setEmail(email);
        user.setEmailVerified(false);
        userRepository.save(user);
        log.info("{} email updated.", user.getUsername());
    }

    @Override
    @Transactional
    public void updatePassword(Authentication authentication, String password) {
        User user = authenticatedUserService.getCurentUser(authentication);

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("{} password updated.", user.getUsername());
    }
}
